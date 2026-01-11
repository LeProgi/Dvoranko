function CategorySelector({ categories, selectedCategories, setSelectedCategories }) {
    const toggleCategory = (cat) => {
        if (selectedCategories.includes(cat)) {
            setSelectedCategories(selectedCategories.filter(c => c !== cat));
        } else {
            setSelectedCategories([...selectedCategories, cat]);
        }
    };
    return (
        <div className="flex flex-wrap gap-[5px] w-[90%]">
            {categories.map((cat) => (
                <button
                key={cat.idKategorija}
                type="button"
                onClick={() => toggleCategory(cat.idKategorija)}
                className={`px-2.5 py-1.5 rounded-[20px] border-2 border-[#3B5B80] font-bold cursor-pointer transition-colors duration-200
                    ${selectedCategories.includes(cat.idKategorija) ? "bg-[#3B5B80] text-white hover:bg-[#2F4B6A]" : "bg-white text-[#3B5B80] hover:bg-[#eee]"}`}
                >
                {cat.nazivKategorije}
                </button>
            ))}
        </div>
    );
}

export default CategorySelector;