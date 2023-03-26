# Base image
FROM node:19-alpine

EXPOSE 3000

# Working directory
WORKDIR /frontend

# Copy the package.json and yarn.lock files
COPY package.json yarn.lock ./

# Copy the rest of the project files
COPY . ./

# Install dependencies
RUN yarn

# Build the project
RUN yarn build

# # Set the command to start the server
# CMD ["yarn", "start"]