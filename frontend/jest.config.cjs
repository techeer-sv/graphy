module.exports = {
  roots: ['<rootDir>'],
  preset: 'ts-jest',
  testEnvironment: 'jsdom',
  moduleNameMapper: {
    '\\.(png|jpg|webp|ttf|woff|woff2|svg|mp4)$':
      '<rootDir>/src/test/jest/__mocks__/file-mock.js',
  },
  transform: {
    '^.+\\.tsx?$': 'ts-jest',
  },
};
