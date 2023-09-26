'use client'

export default function Error({
  error,
  reset,
}: {
  error: Error & { digest?: string }
  reset: () => void
}) {
  return (
    <div>
      <h2>{error.message}</h2>
      <button type="button" onClick={() => reset()}>
        Try again
      </button>
    </div>
  )
}
