import BookCardPlaceholder from "../../components/BookCardPlaceholder.tsx";
import ReviewCard from "../../components/reviews/ReviewCard.tsx";

export default function ReviewBorrower () {
    return(
        <>
            <div className="main-class"
                 style={{display: 'flex', justifyContent: 'center', alignItems: 'center', flexDirection: 'column'}}>


                <div className="container-row-wrapped">
                    <div className="d-flex align-items-center justify-content-center">
                        <BookCardPlaceholder/>
                    </div>
                    <div className="">
                        <div style={{display: 'flex', flexDirection: 'column', justifyContent: 'space-between', maxWidth: '800px'}}>
                            <ReviewCard/>
                            <ReviewCard/>
                        </div>
                    </div>

                </div>

            </div>
        </>
    )
}