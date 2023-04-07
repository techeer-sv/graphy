FROM node:latest

WORKDIR /frontend

COPY . ./

RUN yarn

RUN yarn build

CMD [ "yarn", "dev" ]