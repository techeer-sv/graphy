import './globals.css'
import type { Metadata } from 'next'
import { Inter } from 'next/font/google'
import Provider from '../components/general/Provider'

const inter = Inter({ subsets: ['latin'] })

export const metadata: Metadata = {
  title: 'Graphy',
  description: 'Project Share platform',
}

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <html lang="en">
      <body className={inter.className}>
        <Provider>{children}</Provider>
      </body>
    </html>
  )
}
