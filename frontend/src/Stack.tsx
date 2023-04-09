import Android_studio from './assets/image/stacklogo/Android_studio.svg';
import Apache_cassandra from './assets/image/stacklogo/Apache_cassandra.svg';
import Apache_kafka from './assets/image/stacklogo/Apache_kafka.svg';
import Apache_spark from './assets/image/stacklogo/Apache_spark.svg';
import Aws from './assets/image/stacklogo/AWS.svg';
import Clang from './assets/image/stacklogo/Clang.svg';
import Cpp from './assets/image/stacklogo/Cpp.svg';
import CSharp from './assets/image/stacklogo/CSharp.svg';
import Django from './assets/image/stacklogo/Django.svg';
import Docker from './assets/image/stacklogo/Docker.svg';
import Express from './assets/image/stacklogo/Express.svg';
import Fastapi from './assets/image/stacklogo/Fastapi.svg';
import Firebase from './assets/image/stacklogo/Firebase.svg';
import Flask from './assets/image/stacklogo/Flask.svg';
import Flutter from './assets/image/stacklogo/Flutter.svg';
import Github_actions from './assets/image/stacklogo/Github_actions.svg';
import Go from './assets/image/stacklogo/Go.svg';
import GraphQL from './assets/image/stacklogo/GraphQL.svg';
import Java from './assets/image/stacklogo/Java.svg';
import Javascript from './assets/image/stacklogo/Javascript.svg';
import Jenkins from './assets/image/stacklogo/Jenkins.svg';
import Julia from './assets/image/stacklogo/Julia.svg';
import Kotlin from './assets/image/stacklogo/Kotlin.svg';
import Kubernetes from './assets/image/stacklogo/Kubernetes.svg';
import Mongodb from './assets/image/stacklogo/Mongodb.svg';
import Mysql from './assets/image/stacklogo/Mysql.svg';
import Nextjs from './assets/image/stacklogo/Nextjs.svg';
import Nodejs from './assets/image/stacklogo/Nodejs.svg';
import Nestjs from './assets/image/stacklogo/Nestjs.svg';
import Php from './assets/image/stacklogo/Php.svg';
import Python from './assets/image/stacklogo/Python.svg';
import PyTorch from './assets/image/stacklogo/PyTorch.svg';
import React from './assets/image/stacklogo/React.svg';
import React_native from './assets/image/stacklogo/React_native.svg';
import Redux from './assets/image/stacklogo/Redux.svg';
import Redis from './assets/image/stacklogo/Redis.svg';
import Rust from './assets/image/stacklogo/Rust.svg';
import Spring from './assets/image/stacklogo/Spring.svg';
import Svelte from './assets/image/stacklogo/Svelte.svg';
import Swift from './assets/image/stacklogo/Swift.svg';
import Tensorflow from './assets/image/stacklogo/Tensorflow.svg';
import Typescript from './assets/image/stacklogo/Typescript.svg';
import Unity from './assets/image/stacklogo/Unity.svg';
import Vue from './assets/image/stacklogo/Vue.svg';

class AllStack {
  name: string;
  image: string;
  constructor(name: string, image: string) {
    this.name = name;
    this.image = image;
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
  new AllStack('Javascript', Javascript),
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
  new AllStack('Tensorflow', Tensorflow),
  new AllStack('TypeScrips', Typescript),
  new AllStack('Unity', Unity),
  new AllStack('Vue', Vue),
];

export default AllStacks;
