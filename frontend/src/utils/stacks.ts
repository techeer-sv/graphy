import Android_studio from '../../public/images/svg/stacklogo/Android_studio.svg'
import Apache_cassandra from '../../public/images/svg/stacklogo/Apache_cassandra.svg'
import Apache_kafka from '../../public/images/svg/stacklogo/Apache_kafka.svg'
import Apache_spark from '../../public/images/svg/stacklogo/Apache_spark.svg'
import Aws from '../../public/images/svg/stacklogo/AWS.svg'
import Clang from '../../public/images/svg/stacklogo/Clang.svg'
import Cpp from '../../public/images/svg/stacklogo/Cpp.svg'
import CSharp from '../../public/images/svg/stacklogo/CSharp.svg'
import Django from '../../public/images/svg/stacklogo/Django.svg'
import Docker from '../../public/images/svg/stacklogo/Docker.svg'
import Express from '../../public/images/svg/stacklogo/Express.svg'
import Fastapi from '../../public/images/svg/stacklogo/Fastapi.svg'
import Firebase from '../../public/images/svg/stacklogo/Firebase.svg'
import Flask from '../../public/images/svg/stacklogo/Flask.svg'
import Flutter from '../../public/images/svg/stacklogo/Flutter.svg'
import Github_actions from '../../public/images/svg/stacklogo/Github_actions.svg'
import Go from '../../public/images/svg/stacklogo/Go.svg'
import GraphQL from '../../public/images/svg/stacklogo/GraphQL.svg'
import Java from '../../public/images/svg/stacklogo/Java.svg'
import JavaScript from '../../public/images/svg/stacklogo/JavaScript.svg'
import Jenkins from '../../public/images/svg/stacklogo/Jenkins.svg'
import Julia from '../../public/images/svg/stacklogo/Julia.svg'
import Kotlin from '../../public/images/svg/stacklogo/Kotlin.svg'
import Kubernetes from '../../public/images/svg/stacklogo/Kubernetes.svg'
import Mongodb from '../../public/images/svg/stacklogo/Mongodb.svg'
import Mysql from '../../public/images/svg/stacklogo/Mysql.svg'
import Nestjs from '../../public/images/svg/stacklogo/Nestjs.svg'
import Nextjs from '../../public/images/svg/stacklogo/Nextjs.svg'
import Nodejs from '../../public/images/svg/stacklogo/Nodejs.svg'
import Php from '../../public/images/svg/stacklogo/Php.svg'
import Python from '../../public/images/svg/stacklogo/Python.svg'
import PyTorch from '../../public/images/svg/stacklogo/PyTorch.svg'
import React from '../../public/images/svg/stacklogo/React.svg'
import React_native from '../../public/images/svg/stacklogo/React_native.svg'
import Redis from '../../public/images/svg/stacklogo/Redis.svg'
import Redux from '../../public/images/svg/stacklogo/Redux.svg'
import Rust from '../../public/images/svg/stacklogo/Rust.svg'
import Spring from '../../public/images/svg/stacklogo/Spring.svg'
import Svelte from '../../public/images/svg/stacklogo/Svelte.svg'
import Swift from '../../public/images/svg/stacklogo/Swift.svg'
import TensorFlow from '../../public/images/svg/stacklogo/TensorFlow.svg'
import Typescript from '../../public/images/svg/stacklogo/Typescript.svg'
import Unity from '../../public/images/svg/stacklogo/Unity.svg'
import Vue from '../../public/images/svg/stacklogo/Vue.svg'

class AllStack {
  name: string

  image: string

  constructor(name: string, image: string) {
    this.name = name
    this.image = image
  }
}

const AllStacks = [
  new AllStack('Android Studio', Android_studio),
  new AllStack('Apache Cassandra', Apache_cassandra),
  new AllStack('Apache Kafka', Apache_kafka),
  new AllStack('Apache Spark', Apache_spark),
  new AllStack('AWS', Aws),
  new AllStack('C', Clang),
  new AllStack('C++', Cpp),
  new AllStack('C#', CSharp),
  new AllStack('Django', Django),
  new AllStack('Docker', Docker),
  new AllStack('Express', Express),
  new AllStack('FastAPI', Fastapi),
  new AllStack('Firebase', Firebase),
  new AllStack('Flask', Flask),
  new AllStack('Flutter', Flutter),
  new AllStack('Github actions', Github_actions),
  new AllStack('Go', Go),
  new AllStack('GraphQL', GraphQL),
  new AllStack('Java', Java),
  new AllStack('JavaScript', JavaScript),
  new AllStack('Jenkins', Jenkins),
  new AllStack('Julia', Julia),
  new AllStack('Kotlin', Kotlin),
  new AllStack('Kubernetes', Kubernetes),
  new AllStack('MongoDB', Mongodb),
  new AllStack('MYSQL', Mysql),
  new AllStack('Nextjs', Nextjs),
  new AllStack('Nodejs', Nodejs),
  new AllStack('Nestjs', Nestjs),
  new AllStack('php', Php),
  new AllStack('Python', Python),
  new AllStack('PyTorch', PyTorch),
  new AllStack('React', React),
  new AllStack('ReactNative', React_native),
  new AllStack('Redux', Redux),
  new AllStack('Redis', Redis),
  new AllStack('Rust', Rust),
  new AllStack('Spring', Spring),
  new AllStack('Svelte', Svelte),
  new AllStack('Swift', Swift),
  new AllStack('TensorFlow', TensorFlow),
  new AllStack('TypeScript', Typescript),
  new AllStack('Unity', Unity),
  new AllStack('Vue', Vue),
]

export default AllStacks
