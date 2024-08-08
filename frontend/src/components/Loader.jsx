import { Loader2 } from "lucide-react"

const Loader = () => {
    return (
        <div className="flex items-center justify-center w-screen h-screen">
            <Loader2 className="size-14 animate-spin" />
        </div>
    )
}

export default Loader