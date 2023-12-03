import React from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import Grid from '@mui/material/Grid';
import Pagination from '@mui/material/Pagination';
import Stack from '@mui/system/Stack';
import { styled } from '@mui/system';
import FormGroup from '@mui/material/FormGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import Filters from '../components/Filters';
import BookCard from '../components/BookCard';

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

    const Item = styled('div')(({ theme }) => ({
        // backgroundColor: theme.palette.mode === 'dark' ? '#262B32' : '#fff',
        padding: theme.spacing(2),
        textAlign: 'center',
        borderRadius: 15,
      }));

    return (
        <>
            <Box
                component="form"
                sx={{
                    display: 'flex',
                    justifyContent: 'center',
                    flexDirection: 'column', // Optional: Align vertically as well
                    alignItems: 'center', // Optional: Align vertically as well
                    '& > :not(style)': { marginTop: 4, marginBottom: 2, width: '50%' },
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

            <Grid container spacing={2}  sx={{padding: 2}}>
                <Grid item xs={2}>
                    <Filters></Filters>
                </Grid>
                <Grid item xs={10}>
                    <Box className='bg-light-green' sx={{ width: '100%' , height: '100%'}}>
                        <br></br>
                        <BookCard></BookCard>
                    </Box>
                </Grid>                    
            </Grid>
            <Box className='p-5' sx={{ width: '100%', display: 'flex', justifyContent: 'center'}}>
                <Pagination count={10} color="primary" />
            </Box>

        </>
    );
};

export default DiscoveryView;
