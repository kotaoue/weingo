export interface WeightDataPoint {
  date: string;
  weightKg: number;
}

export interface FitnessWeightResponse {
  latest: WeightDataPoint | null;
  history: WeightDataPoint[];
}

const GOOGLE_FITNESS_API =
  "https://www.googleapis.com/fitness/v1/users/me/dataset:aggregate";

/**
 * Fetch the latest body weight (and up to 30 days history) from Google Fitness REST API.
 */
export async function fetchLatestWeightFromFitness(
  accessToken: string
): Promise<FitnessWeightResponse> {
  const endTimeMs = Date.now();
  // 30 days look-back
  const startTimeMs = endTimeMs - 30 * 24 * 60 * 60 * 1000;

  const body = {
    aggregateBy: [
      {
        dataTypeName: "com.google.weight",
        dataSourceId:
          "derived:com.google.weight:com.google.android.gms:merge_weight",
      },
    ],
    bucketByTime: { durationMillis: 86400000 }, // 1 day buckets
    startTimeMillis: startTimeMs,
    endTimeMillis: endTimeMs,
  };

  const res = await fetch(GOOGLE_FITNESS_API, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${accessToken}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(body),
  });

  if (!res.ok) {
    throw new Error(`Fitness API error: ${res.status} ${res.statusText}`);
  }

  const json = await res.json();
  const history: WeightDataPoint[] = [];

  for (const bucket of json.bucket ?? []) {
    for (const dataset of bucket.dataset ?? []) {
      for (const point of dataset.point ?? []) {
        const weightKg = point.value?.[0]?.fpVal as number | undefined;
        if (weightKg !== undefined) {
          const startNs = parseInt(point.startTimeNanos as string, 10);
          const date = new Date(startNs / 1_000_000).toLocaleDateString(
            "ja-JP",
            { year: "numeric", month: "2-digit", day: "2-digit" }
          );
          history.push({ date, weightKg: Math.round(weightKg * 10) / 10 });
        }
      }
    }
  }

  history.sort((a, b) => (a.date > b.date ? 1 : -1));

  return {
    latest: history.length > 0 ? history[history.length - 1] : null,
    history,
  };
}
