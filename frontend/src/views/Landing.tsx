import NavBar from "../components/NavBar.tsx";
import {Link} from "react-router-dom";

export default function Landing(){
    return (
        <>
        <section className="bg-gray-900 text-white">
            <div
                className="mx-auto max-w-screen-xl px-4 py-32 lg:flex lg:h-screen lg:items-center"
            >
                <div className="mx-auto max-w-3xl text-center">
                    <h1
                        className="bg-gradient-to-r from-green-300 via-blue-500 to-purple-600 bg-clip-text text-3xl font-extrabold text-transparent sm:text-5xl"
                    >
                        <span className="sm:block font-primary text-secondary"> The new era of </span>
                        reading
                    </h1>

                    <p className="mx-auto mt-4 max-w-xl sm:text-xl/relaxed font-primary text-secondary">
                        A shared book is a multiplied story
                    </p>

                    <div className="mt-8 flex flex-wrap justify-center gap-4">
                        {/*<a*/}
                        {/*    className="block w-full rounded border border-dark-green bg-dark-green px-12 py-3 text-sm font-medium text-light-green hover:bg-light-green hover:text-dark-green focus:outline-none focus:ring active:text-opacity-75 sm:w-auto"*/}
                        {/*    href="/get-started"*/}
                        {/*>*/}
                        {/*    Explore*/}
                        {/*</a>*/}
                        <Link to="/discovery">Home</Link>
                    </div>
                </div>
            </div>

            </section>
        </>

    );
}