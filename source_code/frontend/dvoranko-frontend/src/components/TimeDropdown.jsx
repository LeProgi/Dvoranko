import { useState, useRef, useEffect } from "react";

function TimeDropdown({ value, onChange, options, disabled }) {
    const [isOpen, setIsOpen] = useState(false);
    const containerRef = useRef(null);

    useEffect(() => {
        const handleClickOutside = (e) => {
            if (containerRef.current && !containerRef.current.contains(e.target)) {
                setIsOpen(false);
            }
        };
        document.addEventListener("mousedown", handleClickOutside);
        return () => document.removeEventListener("mousedown", handleClickOutside);
    }, []);

    return (
        <div
            ref={containerRef}
            className={`relative w-[40px] ${disabled ? "cursor-not-allowed" : "cursor-pointer"}`}
        >
            <div
                onClick={() => setIsOpen(!isOpen)}
                className={`h-[30px] border-2 border-black rounded-[4px] flex items-center justify-center ${disabled ? "bg-[#ccc]" : "bg-white"}`}
            >
                {value || "--"}
            </div>

            {isOpen && !disabled && (
                <div className="absolute top-[30px] left-0 right-0 max-h-[130px] overflow-y-auto border-2 border-black rounded-[4px] bg-white z-[1000]">
                    {options.map((opt) => (
                        <div
                            id={ `time-option-${opt}` }
                            key={opt}
                            onClick={() => {
                                onChange(opt);
                                setIsOpen(false);
                            }}
                            className={`p-1 text-center cursor-pointer ${value === opt ? "bg-[#ccc]" : "bg-white"} hover:bg-[#eee]`}
                        >
                            {opt}
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}

export default TimeDropdown;
