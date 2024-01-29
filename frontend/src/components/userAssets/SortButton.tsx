
const SortButton = ({ title, onSortChange, currentSortAttribute, currentDirection }: any) => {
    const handleSort = () => {
        // Determine the new sorting direction
        const newDirection = currentSortAttribute === title && currentDirection === 'asc' ? 'desc' : 'asc';
        onSortChange(title, newDirection);
    };

    return (
        <th>
            <button onClick={handleSort} className={`sort-button ${currentSortAttribute === title ? 'sort-button-selected' : ''}`}>
                {title}
                <i className={`fas fa-arrow-${currentSortAttribute === title && currentDirection === 'asc' ? 'up' : 'down'}`}></i>
            </button>
        </th>
    );
};

export default SortButton;
