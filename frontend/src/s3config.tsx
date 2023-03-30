const s3Config = {
  bucketName: `${process.env.VITE_APP_BUCKET_NAME}`,
  region: `${process.env.VITE_APP_REGION}`,
  accessKeyId: `${process.env.VITE_APP_ACCESS}`,
  secretAccessKey: `${process.env.VITE_APP_SECRET}`,
};

export default s3Config;
