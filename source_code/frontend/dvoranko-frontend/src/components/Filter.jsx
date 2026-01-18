import { useState } from "react";
import Button from "./Button";

const Filter = ({ onApply , categories}) => {
    const [open, setOpen] = useState(false);
    const [capacity, setCapacity] = useState("");
    const [zip, setZip] = useState("");
    const [price, setPrice] = useState("");
    const [category, setCategory] = useState("");


    const apply = () => {
        onApply({
            capacity,
            zip, 
            price,
            category
        });
        setOpen(false);
    };

    return (
        <>
            {/* FILTER BAR */}
            <div className="flex flex-row gap-4 w-3/4 h-10 bg-[#d9d9d9] rounded-[10px] justify-start mt-4 mb-4 pr-8">
                <Button
                    variant="filter"
                    className="flex justify-center items-center p-2"
                    onClick={() => setOpen(true)}
                >
                    <div className="w-4 h-4 rounded-full bg-white"></div>
                </Button>
                <div className="text-l text-[#3B5B80] font-semibold mt-2">
                    Filtriraj
                </div>
            </div>

            {/* MODAL */}
            {open && (
                <div className="fixed inset-0 bg-black/40 flex justify-center items-center z-50">
                    <div className="bg-white p-6 rounded-xl w-[400px] shadow-xl">

                        <h2 className="text-xl font-bold mb-4">Filtriranje</h2>

                        {/* Kapacitet */}
                        <div className="mb-4">
                            <label className="font-semibold">Kapacitet</label>
                            <select
                                className="w-full border p-2 rounded"
                                value={capacity}
                                onChange={e => setCapacity(e.target.value)}
                            >
                                <option value="">Sve</option>
                                <option value="0-20">0-20</option>
                                <option value="20-50">20-50</option>
                                <option value="50-70">50-70</option>
                                <option value="70+">70+</option>
                            </select>
                        </div>

                        
                        <div className="mb-4">
                            <label className="font-semibold">Kategorija</label>
                                <select
                                    className="w-full border p-2 rounded"
                                    value={category}
                                    onChange={e => setCategory(e.target.value)}
                                >
                                <option value="">Sve</option>
                                {categories.map(cat => (
                                    <option key={cat} value={cat}>{cat}</option>
                                ))}
                                </select>
                        </div>


                        
                        <div className="mb-4">
                            <label className="font-semibold">Cijena po satu - €</label>
                            <select
                                className="w-full border p-2 rounded"
                                value={price}
                                onChange={e => setPrice(e.target.value)}
                            >
                                <option value="">Sve</option>
                                <option value="0-20">0-20</option>
                                <option value="20-40">20-40</option>
                                <option value="40-60">40-60</option>
                                <option value="60+">60+</option>
                            </select>
                        </div>

                        
                        <div className="flex justify-end gap-4 mt-6">
                            <button
                                className="px-4 py-2 bg-gray-300 rounded"
                                onClick={() => setOpen(false)}
                            >
                                Odustani
                            </button>
                            <button
                                className="px-4 py-2 bg-[#3B5B80] text-white rounded"
                                onClick={apply}
                            >
                                Primijeni
                            </button>
                        </div>

                    </div>
                </div>
            )}
        </>
    );
};

export default Filter;
