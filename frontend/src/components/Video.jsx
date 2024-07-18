import React, { useEffect, useRef } from 'react';

const VideoPlayer = () => {
    const videoRef = useRef(null);

    useEffect(() => {
        if (videoRef.current) {
            videoRef.current.src = 'http://localhost:8080/api/v1/videos/rickRoll';
        }
    }, []);

    return (
        <video
            ref={videoRef}
            autoPlay
            loop
            width="640"
            height="360"
        >
            Your browser does not support the video tag.
        </video>
    );
};

export default VideoPlayer;