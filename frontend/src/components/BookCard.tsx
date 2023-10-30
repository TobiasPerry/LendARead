import React from 'react';

function BookCard({ showSearchbar }) {
    return (
        <nav className="navbar navbar-expand-lg" style={{ backgroundColor: '#111711' }} data-bs-theme="dark">
            {/* ... Your NavBar code */}
            {showSearchbar && (
                <div className="form mx-3" style={{ marginBlockEnd: 0 }}>
                    <i className="fa fa-search fa-search-class"></i>
                    <input
                        type="text"
                        className="form-input"
                        name="search"
                        style={{ marginLeft: '4px' }}
                        placeholder="Search..."
                        id="nav-bar-search-bar"
                    />
                </div>)
            }
            {/* ... Your NavBar code */}
        </nav>
    );
}

export default BookCard;
