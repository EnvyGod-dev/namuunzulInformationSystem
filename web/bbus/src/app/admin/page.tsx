'use client';
import React from 'react';
import { Box, Typography, Grid, Paper } from '@mui/material';
import { styled } from '@mui/material/styles';
import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Tooltip, Legend } from 'chart.js';
import { Line } from 'react-chartjs-2';
import AdminLayout from '@/components/navbar/admin/adminLayout';

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Tooltip, Legend);

const StatCard = styled(Paper)(({ theme }) => ({
    padding: '20px',
    textAlign: 'center',
    background: 'rgba(255, 255, 255, 0.1)',
    color: '#fff',
    boxShadow: '0 4px 6px rgba(0,0,0,0.2)',
}));

const data = {
    labels: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
    datasets: [
        {
            label: 'Income',
            data: [400, 500, 600, 700, 800, 900, 1000],
            borderColor: '#4bc0c0',
            backgroundColor: 'rgba(75, 192, 192, 0.2)',
            tension: 0.4,
        },
        {
            label: 'Expense',
            data: [200, 300, 400, 500, 600, 700, 800],
            borderColor: '#ff6384',
            backgroundColor: 'rgba(255, 99, 132, 0.2)',
            tension: 0.4,
        },
    ],
};

const AdminDashboard = () => {
    return (
        <AdminLayout>
            <Typography variant="h4" gutterBottom>
                Welcome to the Admin Dashboard
            </Typography>
            <Grid container spacing={3}>
                {[{
                    title: 'Income', value: '$45,234', change: '-2% than last month'
                }, {
                    title: 'Expense', value: '$19,522', change: '+0.5% than last month'
                }, {
                    title: 'Transactions', value: '$984', change: '38% this week'
                }, {
                    title: 'Admission Summary', value: '$4,563', change: '+1.6% last week'
                }].map(({ title, value, change }, idx) => (
                    <Grid item xs={12} md={6} lg={3} key={idx}>
                        <StatCard>
                            <Typography variant="h6">{title}</Typography>
                            <Typography variant="h4">{value}</Typography>
                            <Typography variant="body2">{change}</Typography>
                        </StatCard>
                    </Grid>
                ))}
                <Grid item xs={12} lg={6}>
                    <StatCard>
                        <Typography variant="h6">Your Balance Summary</Typography>
                        <Line data={data} />
                    </StatCard>
                </Grid>
                <Grid item xs={12} lg={6}>
                    <StatCard>
                        <Typography variant="h6">Your Pie Chart</Typography>
                        <Box
                            sx={{
                                height: 300,
                                display: 'flex',
                                justifyContent: 'center',
                                alignItems: 'center',
                            }}
                        >
                            Pie Chart Placeholder
                        </Box>
                    </StatCard>
                </Grid>
            </Grid>
        </AdminLayout>
    );
};

export default AdminDashboard;
