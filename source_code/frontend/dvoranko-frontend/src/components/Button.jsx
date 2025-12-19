import * as React from "react"
import {cva} from "class-variance-authority"
import {cn} from "../lib/utils"

const buttonVariants = cva(
    "text-[15px] rounded-[10px] cursor-pointer font-medium transition-colors",
    {
        variants:{
            variant:{
                default: "w-full p-[15px] mt-[10px] bg-[#536F8F] text-[#f5f5f5] hover:bg-[#4E6987]",
                main: "w-1/2 flex items-center justify-center mt-[10%] ml-[25%] mr-[25%] bg-[#3B5B80] text-white p-[10px] hover:bg-[#2F4B6A]",
                profile:"flex items-center justify-center w-[50px] h-[50px] p-[10px] mt-[10px] rounded-full bg-white text-[#3B5B80]"
            },
            defaultVariants:{
                variant: "default",

            }
        }
    }
)
const Button = ({ title, variant, onClick }) => {
  return (
    <button
      onClick={onClick}
      className={cn(buttonVariants({ variant }))}
    >
      {title}
    </button>
  );
};

export default Button;
