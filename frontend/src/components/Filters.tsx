import React from 'react';
import Box from '@mui/material/Box';
import Stack from '@mui/system/Stack';
import FormGroup from '@mui/material/FormGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import { styled } from '@mui/system';

const Item = styled('div')(({ theme }) => ({
    // backgroundColor: theme.palette.mode === 'dark' ? '#262B32' : '#fff',
    padding: theme.spacing(2),
    textAlign: 'center',
    borderRadius: 15,
  }));

const Filters = () => {
    return (
        <Box sx={{ width: '100%' }}>
            <Stack spacing={2}>
                <Item className='text-primary bg-light-green'>
                    <FormGroup>
                        Texto
                        <FormControlLabel control={<Checkbox defaultChecked />} label="Label" />
                        <FormControlLabel control={<Checkbox defaultChecked />} label="Label" />
                        <FormControlLabel control={<Checkbox defaultChecked />} label="Label" />
                    </FormGroup>
                </Item>
                <Item className='text-primary bg-light-green'>
                    <FormGroup>
                        Texto
                        <FormControlLabel control={<Checkbox defaultChecked />} label="Label" />
                        <FormControlLabel control={<Checkbox defaultChecked />} label="Label" />
                    </FormGroup>
                </Item>
                <Item className='text-primary bg-light-green'>
                    <FormGroup>
                        Texto
                        <FormControlLabel control={<Checkbox defaultChecked />} label="Label" />
                        <FormControlLabel control={<Checkbox defaultChecked />} label="Label" />
                        <FormControlLabel control={<Checkbox defaultChecked />} label="Label" />
                        <FormControlLabel control={<Checkbox defaultChecked />} label="Label" />
                        <FormControlLabel control={<Checkbox defaultChecked />} label="Label" />
                    </FormGroup>
                </Item>
            </Stack>
        </Box>
    );
}

export default Filters;
