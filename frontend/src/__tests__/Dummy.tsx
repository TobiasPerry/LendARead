import { render, screen } from '@testing-library/react';
import React from 'react'; // Add this line
import Dummy from "../views/dummyView.tsx";
import {setupTests} from "../testUtils/setupTests.js.ts";

setupTests()
describe('Landing Component', () => {
    test('renders without crashing', () => {
        render(
           <Dummy />
        );
        const titleElement = screen.getByText("does this pass tests??");
        expect(titleElement).toBeInTheDocument();
    });
});
