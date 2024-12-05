'use client';
import React, { useState } from 'react';
import { styled } from '@mui/material/styles';
import { Box } from '@mui/material';
import AdminNavbar from '.';

const LayoutContainer = styled(Box)(({ theme }) => ({
    display: 'flex',
    height: '100vh',
    overflow: 'hidden',
}));

const ContentContainer = styled(Box)(({ theme }) => ({
    flex: 1,
    marginLeft: '20%',
    padding: '20px',
    backgroundImage: "linear-gradient(to bottom, #2c2e53, #191a37)",
    backgroundSize: 'cover',
    backgroundPosition: 'center',
    overflowY: 'auto',
    [theme.breakpoints.down('md')]: {
        marginLeft: '80%',
    },
}));

interface AdminLayoutProps {
    children: React.ReactNode;
}

const AdminLayout: React.FC<AdminLayoutProps> = ({ children }) => {
    const [activeItem, setActiveItem] = useState('Dashboard');

    return (
        <LayoutContainer>
            <AdminNavbar activeItem={activeItem} setActiveItem={setActiveItem} />
            <ContentContainer>{children}</ContentContainer>
        </LayoutContainer>
    );
};

export default AdminLayout;
