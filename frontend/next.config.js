/** @type {import('next').NextConfig} */
const awsS3Bucket = process.env.BUCKET_NAME || ''
const awsS3BucketRegion = process.env.REGION || 'ap-northeast-2'

const nextConfig = {
  images: {
    remotePatterns: [
      {
        protocol: 'https',
        hostname: `${awsS3Bucket}.s3-${awsS3BucketRegion}.amazonaws.com`,
      },
    ],
  },
}

module.exports = nextConfig
