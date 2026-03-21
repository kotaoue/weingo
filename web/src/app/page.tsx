import { getServerSession } from "next-auth";
import { authOptions } from "@/lib/authOptions";
import WeightTracker from "@/components/WeightTracker";
import SignIn from "@/components/SignIn";

export default async function Home() {
  const session = await getServerSession(authOptions);

  return (
    <main className="max-w-md mx-auto px-4 py-8">
      <h1 className="text-2xl font-bold text-center text-gray-800 mb-2">
        🏋️ weingo
      </h1>
      <p className="text-center text-gray-500 text-sm mb-8">体重管理・予想アプリ</p>
      {session ? <WeightTracker session={session} /> : <SignIn />}
    </main>
  );
}
