'use client';
import React, { useState } from 'react';
import { styled } from '@mui/material/styles';
import { Drawer, List, ListItemButton, ListItemIcon, ListItemText, Typography, Box } from '@mui/material';
import HomeIcon from '@mui/icons-material/Home';
import AppsIcon from '@mui/icons-material/Apps';
import BarChartIcon from '@mui/icons-material/BarChart';
import WidgetsIcon from '@mui/icons-material/Widgets';
import TableChartIcon from '@mui/icons-material/TableChart';

const SidebarContainer = styled(Drawer)(({ theme }) => ({
    '& .MuiDrawer-paper': {
        width: '20%', // Set to 20% width
        background: 'linear-gradient(to bottom, #f4f4f9, #d0f1ea)',
        color: '#4a4a4a',
        borderTopRightRadius: '20px',
        borderBottomRightRadius: '20px',
        height: '100vh', // Ensure the full height for the sidebar
        position: 'fixed',
        top: 0,
        left: 0,
    },
}));

const Logo = styled(Box)(({ theme }) => ({
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    padding: '20px 0',
    fontSize: '24px',
    fontWeight: 'bold',
    color: '#4a4a4a',
    backgroundColor: '#d0f1ea',
    borderTopRightRadius: '20px',
}));

const Footer = styled(Box)(({ theme }) => ({
    position: 'absolute',
    bottom: 10,
    textAlign: 'center',
    width: '100%',
    fontSize: '12px',
    color: '#4a4a4a',
    lineHeight: '1.5',
    paddingBottom: '10px',
    borderBottomRightRadius: '20px',
}));

const ListItemButtonStyled = styled(ListItemButton)<{ selected: boolean }>(({ selected }) => ({
    padding: '10px 16px',
    backgroundColor: selected ? '#d0f1ea' : 'transparent',
    '&:hover': {
        backgroundColor: '#e0f7fa',
    },
}));

interface AdminNavbarProps {
    activeItem: string;
    setActiveItem: (item: string) => void;
}

const AdminNavbar: React.FC<AdminNavbarProps> = ({ activeItem, setActiveItem }) => {
    return (
        <SidebarContainer variant="permanent" open>
            <Logo>TechPsychoPath</Logo>
            <List>
                {[
                    { label: 'Dashboard', icon: <HomeIcon /> },
                    { label: 'Apps', icon: <AppsIcon /> },
                    { label: 'Charts', icon: <BarChartIcon /> },
                    { label: 'Widget', icon: <WidgetsIcon /> },
                    { label: 'Table', icon: <TableChartIcon /> },
                ].map(({ label, icon }) => (
                    <ListItemButtonStyled
                        key={label}
                        selected={activeItem === label}
                        onClick={() => setActiveItem(label)}
                    >
                        <ListItemIcon>{icon}</ListItemIcon>
                        <ListItemText primary={label} />
                    </ListItemButtonStyled>
                ))}
            </List>
            <Footer>
                <Typography>Made with 💜 by NamuunZul</Typography>
            </Footer>
        </SidebarContainer>
    );
};

export default AdminNavbar;
