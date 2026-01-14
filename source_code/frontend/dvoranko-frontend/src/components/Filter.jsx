import { useState, useRef, useEffect } from "react";
import Button from "./Button";

const Filter = () => {
    return(
        <div className="flex flex-row gap-4 w-3/4 h-10 bg-[#d9d9d9] rounded-[10px] justify-start mt-4 mb-4 pr-8">
            <Button variant="filter" className="flex justify-center items-center p-2">
                <div className="w-4 h-4 rounded-full bg-white"></div>
            </Button>
            <div className="text-l text-[#3B5B80] font-semibold mt-2">
                Filtriraj
            </div>
        </div>
    )
}

export default Filter;
