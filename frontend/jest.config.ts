// jest.config.ts
import type { Config } from '@jest/types';

const config: Config.InitialOptions = {
    preset: 'ts-jest',
    testEnvironment: 'jest-environment-jsdom',
    setupFilesAfterEnv: ['<rootDir>/src/setupTests.ts'],
    moduleNameMapper: {
        // Handle module aliases (if you are using them in your project)
        '^@/(.*)$': '<rootDir>/src/$1',
        '\\.(jpg|jpeg|png|gif|webp|svg)$': '<rootDir>/__mocks__/fileMock.js',
        '\\.(css|scss|sass)$': '<rootDir>/__mocks__/styleMock.js',
    },
    transform: {
        "^.+\\.(ts|tsx)$": "babel-jest", // Transform TypeScript files
        "^.+\\.(js|jsx)$": "babel-jest", // Transform JavaScript files
    },
    testPathIgnorePatterns: ['/node_modules/', '/dist/'],
    moduleFileExtensions: ['ts', 'tsx', 'js', 'jsx', 'json', 'node'],
};

export default config;
