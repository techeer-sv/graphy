/* eslint-disable no-console */
import { PutObjectCommand, S3Client } from '@aws-sdk/client-s3'
import { NextResponse } from 'next/server'
import type { NextRequest } from 'next/server'
import short from 'short-uuid'

const awsAccessKey = process.env.ACCESS || ''
const awsSecretKey = process.env.SECRET || ''
const awsS3Bucket = process.env.BUCKET_NAME || ''
const awsS3BucketRegion = process.env.REGION || 'ap-northeast-2'

const s3 = new S3Client({
  credentials: {
    accessKeyId: awsAccessKey,
    secretAccessKey: awsSecretKey,
  },
  region: awsS3BucketRegion,
})

const uuid = short.generate()

async function uploadFile(blob: Blob) {
  const arrayBuffer = await blob.arrayBuffer()
  const buffer = Buffer.from(arrayBuffer)
  const key = `${uuid}.${blob.type.split('/')[1]}}`
  const command = new PutObjectCommand({
    Bucket: awsS3Bucket,
    Key: key,
    Body: buffer,
    ContentType: blob.type,
    ACL: 'public-read',
  })

  try {
    const res = await s3.send(command)
    return res
  } catch (err: unknown) {
    console.log(err)
    if (err instanceof Error) {
      throw new Error(err.message)
    }
    return null
  }
}

// eslint-disable-next-line import/prefer-default-export
export async function POST(request: NextRequest) {
  if (!request.formData) {
    return NextResponse.json(
      { message: '이미지를 업로드 해 주세요.' },
      { status: 400 },
    )
  }

  const data = await request.formData()
  const image = data.get('image') as Blob

  if (image && image.type.startsWith('image/')) {
    await uploadFile(image)
    return NextResponse.json({
      message: '이미지 업로드 성공',
      data: {
        location: `https://${awsS3Bucket}.s3-${awsS3BucketRegion}.amazonaws.com/${uuid}.${
          image.type.split('/')[1]
        }`,
      },
    })
  }
  return NextResponse.json(
    { message: '이미지 파일만 업로드 할 수 있습니다.' },
    { status: 400 },
  )
}
