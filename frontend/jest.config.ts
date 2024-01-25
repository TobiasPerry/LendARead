// jest.config.ts
import type { Config } from '@jest/types';

const config: Config.InitialOptions = {
    preset: 'ts-jest',
    testEnvironment: 'jest-environment-jsdom',
    setupFilesAfterEnv: ['<rootDir>/src/__tests__/setupTests.ts'],
    moduleNameMapper: {
        // Handle module aliases (if you are using them in your project)
        '^@/(.*)$': '<rootDir>/src/$1',
    },
    transform: {
        // Transform files with ts-jest
        '^.+\\.(ts|tsx)$': 'ts-jest',
    },
    testPathIgnorePatterns: ['/node_modules/', '/dist/'],
    moduleFileExtensions: ['ts', 'tsx', 'js', 'jsx', 'json', 'node'],
};

export default config;
