// Landing.test.tsx
import { render, screen } from '@testing-library/react';
import Landing from '../views/Landing.tsx';
import React from 'react'; // Add this line
import { I18nextProvider } from 'react-i18next';
import i18n from '../i18n.js'
import AuthContextProvider, {AuthContext} from "../contexts/authContext.tsx";
import App from "../App.tsx";
import Dummy from "../views/dummyView.tsx";

describe('Landing Component', () => {
    test('renders without crashing', () => {
        render(
           <Dummy />
        );
        const titleElement = screen.getByText("does this pass tests??");
        expect(titleElement).toBeInTheDocument();
    });

});
