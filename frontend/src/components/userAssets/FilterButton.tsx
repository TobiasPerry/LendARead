
const FilterButton = ({ buttonText, filterValue, onFilterChange, currentFilter }: any) => {
    const handleFilter = () => {
        onFilterChange(filterValue);
    };

    return (
        <div className="d-inline-flex">
            <button onClick={handleFilter} className={`btn btn-primary ${currentFilter === filterValue ? 'filter-button-selected' : ''}`}>
                {buttonText}
            </button>
        </div>
    );
};

export default FilterButton;
