
import {useTranslation} from 'react-i18next';
const Navbar = () => {
    const { t} = useTranslation();

    return (
        <div className="flex justify-between items-center p-4 bg-gray-800 text-white">
            <div className="text-2xl font-bold">lend a read.</div>

            <div>
                {t('bye')}
            </div>
            <div className="flex-1 px-4">
                <input
                    type="text"
                    placeholder="Search by Author or Title"
                    className="w-full p-2 border border-gray-300 rounded focus:outline-none focus:border-indigo-500"
                />
            </div>
            <div className="space-x-4">
                <a href="#" className="hover:text-gray-400">Explore</a>
                <a href="#" className="hover:text-gray-400">Lend book</a>
                <a href="#" className="hover:text-gray-400">Log In</a>
            </div>
        </div>
    );
}

export default Navbar;
