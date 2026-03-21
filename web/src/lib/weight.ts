/**
 * Calculate the forecast accuracy percentage.
 * accuracy = (actual / forecast) * 100
 */
export function calcAccuracy(actual: number, forecast: number): number {
  if (forecast === 0) return 0;
  return Math.round((actual / forecast) * 1000) / 10;
}

/**
 * Return a Tailwind text-color class based on accuracy.
 *  > 110%  → red   (error)
 *  > 105%  → yellow (warning)
 *  < 95%   → green  (good)
 *  else    → gray
 */
export function accuracyColorClass(accuracy: number): string {
  if (accuracy > 110) return "text-red-600";
  if (accuracy > 105) return "text-yellow-600";
  if (accuracy < 95) return "text-green-600";
  return "text-gray-700";
}

/**
 * Return a human-readable judgment label.
 */
export function accuracyLabel(accuracy: number): string {
  if (accuracy > 110) return "😰 かなりオーバー";
  if (accuracy > 105) return "😐 少しオーバー";
  if (accuracy < 95) return "🎉 よくできました！";
  return "✅ ほぼ予想通り";
}
