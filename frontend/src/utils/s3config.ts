const s3Config = {
  bucketName: process.env.NEXT_PUBLIC_BUCKET_NAME?.replace(/"/g, '') || '',
  region: process.env.NEXT_PUBLIC_REGION?.replace(/"/g, '') || '',
  accessKeyId: process.env.NEXT_PUBLIC_ACCESS?.replace(/"/g, '') || '',
  secretAccessKey: process.env.NEXT_PUBLIC_SECRET?.replace(/"/g, '') || '',
}

export default s3Config
