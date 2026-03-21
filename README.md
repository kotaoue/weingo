# weingo

[![Vercel](https://img.shields.io/badge/vercel-deployed-brightgreen?logo=vercel)](https://weingo.vercel.app/)

Weight tracking & prediction app

## Overview

weingo is an app for recording and managing your daily weight.  
Enter your "predicted weight" and "confirmed weight" for the day, and see how accurate your prediction was.

**Web version** (`web/`) is a mobile-friendly Next.js app.  
It fetches your actual weight from the Google Fitness API and lets you enter a predicted weight based on the difference from your last measurement.  
Ready to deploy on Vercel.

Try it live at **[https://weingo.vercel.app/](https://weingo.vercel.app/)** — no installation needed.

## Features

- Sign in with your Google account
- Automatically fetch the latest weight data from the Google Fitness API
- Enter a predicted weight as a delta (±kg) from the previous measurement
- Automatically calculate and color-display prediction accuracy vs. actual weight
- View weight history for the past 30 days
- Mobile-friendly UI
- Ready to deploy on Vercel

## Tech Stack

| Item | Details |
|------|---------|
| Framework | Next.js 15 (App Router) |
| Language | TypeScript |
| Styling | Tailwind CSS |
| Auth | NextAuth.js (Google OAuth2) |
| Data source | Google Fitness REST API |
| Deployment | Vercel |

## Setup

#### 1. Google Cloud Console

1. Create a project in [Google Cloud Console](https://console.cloud.google.com/)
2. Enable the **Fitness API**
3. Create an **OAuth 2.0 Client ID** (Application type: Web application)
4. Add the following to the authorized redirect URIs:
   - `http://localhost:3000/api/auth/callback/google` (local development)
   - `https://your-app.vercel.app/api/auth/callback/google` (Vercel deployment)

#### 2. Local Development

```bash
cd web
cp .env.local.example .env.local
# Set GOOGLE_CLIENT_ID, GOOGLE_CLIENT_SECRET, NEXTAUTH_SECRET, NEXTAUTH_URL in .env.local

npm install
npm run dev
```

Open http://localhost:3000 in your browser.

#### 3. Deploy to Vercel

```bash
# Using the Vercel CLI
npm i -g vercel
cd web
vercel

# Or connect your GitHub repository to Vercel for automatic deployments
```

Set the following environment variables in Vercel:

| Variable | Description |
|----------|-------------|
| `GOOGLE_CLIENT_ID` | Google OAuth client ID |
| `GOOGLE_CLIENT_SECRET` | Google OAuth client secret |
| `NEXTAUTH_SECRET` | Session encryption key (generate with `openssl rand -base64 32`) |
| `NEXTAUTH_URL` | Deployment URL (e.g. `https://your-app.vercel.app`) |

## Project Structure

```
weingo/
├── web/                    # Web app (Next.js + Vercel)
│   ├── src/
│   │   ├── app/            # Next.js App Router
│   │   │   ├── api/auth/   # NextAuth.js (Google OAuth2)
│   │   │   ├── api/fitness/# Google Fitness API proxy
│   │   │   ├── layout.tsx
│   │   │   └── page.tsx
│   │   ├── components/
│   │   │   ├── WeightTracker.tsx  # Weight input & prediction screen
│   │   │   └── SignIn.tsx         # Sign-in screen
│   │   └── lib/
│   │       ├── authOptions.ts     # NextAuth config
│   │       ├── fitness.ts         # Google Fitness API client
│   │       └── weight.ts          # Prediction accuracy logic
│   ├── .env.local.example  # Environment variable template
│   └── vercel.json         # Vercel deployment config
```
