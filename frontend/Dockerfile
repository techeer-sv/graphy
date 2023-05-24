FROM node:16-alpine

WORKDIR /frontend

COPY . ./

RUN yarn

RUN yarn build

CMD [ "yarn", "dev" ]