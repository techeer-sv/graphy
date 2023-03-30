const s3Config = {
  bucketName: process.env.VITE_APP_BUCKET_NAME?.replace(/"/g, '') || '',
  region: process.env.VITE_APP_REGION?.replace(/"/g, '') || '',
  accessKeyId: process.env.VITE_APP_ACCESS?.replace(/"/g, '') || '',
  secretAccessKey: process.env.VITE_APP_SECRET?.replace(/"/g, '') || '',
};

export default s3Config;
