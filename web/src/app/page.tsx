"use client";

import { useState } from "react";

export default function Home() {
  const [forecast, setForecast] = useState<string>("");
  const [submitted, setSubmitted] = useState(false);

  function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    if (forecast.trim() !== "") {
      setSubmitted(true);
    }
  }

  function handleReset() {
    setForecast("");
    setSubmitted(false);
  }

  return (
    <main className="max-w-md mx-auto px-4 py-8">
      <h1 className="text-2xl font-bold text-center text-gray-800 mb-2">
        🏋️ weingo
      </h1>
      <p className="text-center text-gray-500 text-sm mb-8">体重管理・予想アプリ</p>

      <div className="bg-white rounded-2xl shadow-sm p-6">
        {!submitted ? (
          <form onSubmit={handleSubmit} className="flex flex-col gap-4">
            <label className="text-gray-700 font-medium" htmlFor="forecast">
              今日の体重予想 (kg)
            </label>
            <input
              id="forecast"
              type="number"
              step="0.1"
              min="0"
              placeholder="例: 65.5"
              value={forecast}
              onChange={(e) => setForecast(e.target.value)}
              className="border border-gray-300 rounded-lg px-4 py-3 text-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
              required
            />
            <button
              type="submit"
              className="bg-blue-500 hover:bg-blue-600 text-white font-semibold rounded-lg px-4 py-3 transition-colors"
            >
              入力する
            </button>
          </form>
        ) : (
          <div className="flex flex-col gap-4 text-center">
            <p className="text-gray-500 text-sm">今日の体重予想</p>
            <p className="text-4xl font-bold text-blue-600">
              {parseFloat(forecast).toFixed(1)} kg
            </p>
            <p className="text-gray-400 text-xs">
              ※ この値は一時的なものです。ページを更新すると消えます。
            </p>
            <button
              onClick={handleReset}
              className="mt-2 bg-gray-100 hover:bg-gray-200 text-gray-700 font-medium rounded-lg px-4 py-2 transition-colors"
            >
              もう一度入力する
            </button>
          </div>
        )}
      </div>
    </main>
  );
}
