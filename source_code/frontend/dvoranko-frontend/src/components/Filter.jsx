import { useState } from "react";
import Button from "./Button";

const Filter = ({ onApply }) => {
    const [open, setOpen] = useState(false);
    const [capacity, setCapacity] = useState("");
    const [zip, setZip] = useState("");

    const apply = () => {
        onApply({
            capacity,
            zip
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

                        {/* Poštanski broj */}
                        <div className="mb-4">
                            <label className="font-semibold">Poštanski broj (Zagreb)</label>
                            <input
                                type="text"
                                placeholder="10110"
                                className="w-full border p-2 rounded"
                                value={zip}
                                onChange={e => setZip(e.target.value)}
                            />
                        </div>

                        {/* Buttons */}
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
