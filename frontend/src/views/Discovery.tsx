import React from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';



const bull = (
    <Box
      component="span"
      sx={{ display: 'inline-block', mx: '2px', transform: 'scale(0.8)' }}
    >
      •
    </Box>
  );

var label = "Search"

const searchBarStyle: React.CSSProperties = {

}

const DiscoveryView = () => {



    return (
        <>
            <Box
                component="form"
                sx={{
                    display: 'flex',
                    justifyContent: 'center',
                    flexDirection: 'column', // Optional: Align vertically as well
                    alignItems: 'center', // Optional: Align vertically as well
                    '& > :not(style)': { m: 5, width: '50%' },
                }}
                noValidate
                autoComplete="off"
                >
                <TextField 
                    fullWidth
                    color='primary'
                    id="outlined-basic"
                    label={label}   
                    variant="outlined" 
                />
            </Box>  
        </>
    );
};

export default DiscoveryView;
