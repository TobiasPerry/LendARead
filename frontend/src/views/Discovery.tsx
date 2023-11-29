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
                    '& > :not(style)': { m: 1, width: '25ch' },
                }}
                noValidate
                autoComplete="off"
                >
                <TextField id="outlined-basic" label={label} variant="outlined" />
                
            </Box>
        </>
    );
};

export default DiscoveryView;
