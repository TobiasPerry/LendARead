
const GreenButton = ({action, text}) => {
    return(
        <>
            <button className="btn btn-green" onClick={action}>
                {text}
            </button>
        </>
    )
}
export default GreenButton;