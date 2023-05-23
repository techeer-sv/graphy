module.exports = {
  roots: ['<rootDir>'],
  preset: 'ts-jest',
  testEnvironment: 'jsdom',
  setupFilesAfterEnv: ['<rootDir>/src/test/jest/setupFile.js'],
  moduleNameMapper: {
    '\\.(png|jpg|webp|ttf|woff|woff2|svg|mp4)$':
      '<rootDir>/src/test/jest/__mocks__/file-mock.js',
  },
  transformIgnorePatterns: ['/node_modules/(?!swiper|ssr-window|dom7)'],
  transform: {
    '^.+\\.tsx?$': 'ts-jest',
    '^.+\\.(ts|js)$': 'babel-jest',
    '^.+\\.(css|scss)$': '<rootDir>/src/test/jest/__mocks__/fileTransform.js',
  },
};
