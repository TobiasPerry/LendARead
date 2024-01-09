import "./styles/loadingAnimation.css"
const LoadingAnimation = () => {
    return (
        <>
            <div className="main-class" style={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                height: '100vh'
            }}>
                <img src="/static/favicon-claro-bg.ico" alt="Animated Image" className="fade-in-out"/>
            </div>
        </>
    )
}

export default LoadingAnimation;