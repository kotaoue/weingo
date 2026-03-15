"use client";

import { useState, useEffect, useCallback } from "react";
import { signOut } from "next-auth/react";
import type { Session } from "next-auth";
import type { FitnessWeightResponse } from "@/lib/fitness";
import { calcAccuracy, accuracyColorClass, accuracyLabel } from "@/lib/weight";

interface Props {
  session: Session;
}

export default function WeightTracker({ session }: Props) {
  const [fitnessData, setFitnessData] = useState<FitnessWeightResponse | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [delta, setDelta] = useState<string>("0");
  const [forecast, setForecast] = useState<number | null>(null);
  const [showHistory, setShowHistory] = useState(false);

  const fetchWeight = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const res = await fetch("/api/fitness");
      if (!res.ok) {
        const data = await res.json();
        throw new Error(data.error ?? "Fitness API error");
      }
      const data: FitnessWeightResponse = await res.json();
      setFitnessData(data);
    } catch (err) {
      setError(err instanceof Error ? err.message : "データ取得に失敗しました");
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchWeight();
  }, [fetchWeight]);

  useEffect(() => {
    if (fitnessData?.latest) {
      const d = parseFloat(delta) || 0;
      setForecast(
        Math.round((fitnessData.latest.weightKg + d) * 10) / 10
      );
    }
  }, [delta, fitnessData]);

  const latest = fitnessData?.latest;
  const accuracy = latest && forecast != null
    ? calcAccuracy(latest.weightKg, forecast)
    : null;

  return (
    <div className="flex flex-col gap-4">
      {/* ユーザー情報 */}
      <div className="flex items-center justify-between bg-white rounded-xl shadow-sm px-4 py-3">
        <div className="flex items-center gap-3">
          {session.user?.image && (
            // eslint-disable-next-line @next/next/no-img-element
            <img
              src={session.user.image}
              alt="avatar"
              className="w-8 h-8 rounded-full"
            />
          )}
          <span className="text-sm font-medium text-gray-700 truncate max-w-[160px]">
            {session.user?.name ?? session.user?.email}
          </span>
        </div>
        <button
          onClick={() => signOut()}
          className="text-xs text-gray-400 hover:text-gray-600 transition-colors"
        >
          サインアウト
        </button>
      </div>

      {/* Google Fit から取得した最新体重 */}
      <div className="bg-white rounded-2xl shadow-md p-6">
        <div className="flex items-center justify-between mb-4">
          <h2 className="font-semibold text-gray-700">📊 最新体重 (Google Fit)</h2>
          <button
            onClick={fetchWeight}
            disabled={loading}
            className="text-xs text-blue-500 hover:text-blue-700 disabled:text-gray-400 transition-colors"
          >
            {loading ? "読み込み中..." : "更新"}
          </button>
        </div>

        {error && (
          <p className="text-red-500 text-sm bg-red-50 rounded-lg p-3">{error}</p>
        )}

        {!loading && !error && (
          <>
            {latest ? (
              <div className="text-center">
                <p className="text-4xl font-bold text-gray-800">
                  {latest.weightKg}
                  <span className="text-xl font-normal text-gray-500 ml-1">kg</span>
                </p>
                <p className="text-sm text-gray-400 mt-1">{latest.date}</p>
              </div>
            ) : (
              <p className="text-center text-gray-400 text-sm py-4">
                Google Fit に体重データがありません
              </p>
            )}
          </>
        )}
      </div>

      {/* 予想体重入力 */}
      <div className="bg-white rounded-2xl shadow-md p-6">
        <h2 className="font-semibold text-gray-700 mb-4">🎯 予想体重を入力</h2>

        <div className="mb-4">
          <label className="block text-sm text-gray-500 mb-1">
            前回計測からの差分 (kg)
          </label>
          <div className="flex items-center gap-2">
            <button
              onClick={() =>
                setDelta((v) => String(Math.round((parseFloat(v || "0") - 0.1) * 10) / 10))
              }
              className="w-10 h-10 rounded-full bg-gray-100 hover:bg-gray-200 text-lg font-bold transition-colors"
            >
              −
            </button>
            <input
              type="number"
              step="0.1"
              value={delta}
              onChange={(e) => setDelta(e.target.value)}
              className="flex-1 text-center text-xl font-semibold border border-gray-200 rounded-xl py-2 focus:outline-none focus:ring-2 focus:ring-blue-300"
            />
            <button
              onClick={() =>
                setDelta((v) => String(Math.round((parseFloat(v || "0") + 0.1) * 10) / 10))
              }
              className="w-10 h-10 rounded-full bg-gray-100 hover:bg-gray-200 text-lg font-bold transition-colors"
            >
              ＋
            </button>
          </div>
        </div>

        {forecast !== null && (
          <div className="bg-blue-50 rounded-xl p-4 text-center">
            <p className="text-sm text-blue-500 mb-1">今日の予想体重</p>
            <p className="text-3xl font-bold text-blue-700">
              {forecast}
              <span className="text-lg font-normal text-blue-500 ml-1">kg</span>
            </p>
          </div>
        )}
      </div>

      {/* 判定結果 */}
      {accuracy !== null && latest && forecast !== null && (
        <div className="bg-white rounded-2xl shadow-md p-6 text-center">
          <h2 className="font-semibold text-gray-700 mb-4">📝 判定結果</h2>
          <div className="flex justify-around text-sm text-gray-500 mb-4">
            <div>
              <p>予想</p>
              <p className="text-lg font-semibold text-gray-700">{forecast} kg</p>
            </div>
            <div className="border-l border-gray-200" />
            <div>
              <p>実測</p>
              <p className="text-lg font-semibold text-gray-700">{latest.weightKg} kg</p>
            </div>
          </div>
          <p className={`text-3xl font-bold mb-2 ${accuracyColorClass(accuracy)}`}>
            {accuracy}%
          </p>
          <p className={`text-lg font-medium ${accuracyColorClass(accuracy)}`}>
            {accuracyLabel(accuracy)}
          </p>
        </div>
      )}

      {/* 履歴 */}
      {fitnessData && fitnessData.history.length > 0 && (
        <div className="bg-white rounded-2xl shadow-md p-6">
          <button
            onClick={() => setShowHistory((v) => !v)}
            className="w-full flex items-center justify-between text-sm font-semibold text-gray-700"
          >
            <span>📅 過去30日の体重履歴</span>
            <span className="text-gray-400">{showHistory ? "▲" : "▼"}</span>
          </button>
          {showHistory && (
            <ul className="mt-4 divide-y divide-gray-100">
              {[...fitnessData.history].reverse().map((d) => (
                <li key={d.date} className="flex justify-between py-2 text-sm">
                  <span className="text-gray-500">{d.date}</span>
                  <span className="font-semibold text-gray-700">{d.weightKg} kg</span>
                </li>
              ))}
            </ul>
          )}
        </div>
      )}
    </div>
  );
}
