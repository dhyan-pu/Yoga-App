﻿namespace MauiApp3
{
    public partial class App : Application
    {
        public App()
        {
            InitializeComponent();

            MainPage = new NavigationPage(new LoginPage());
            Routing.RegisterRoute("HomePage", typeof(HomePage));
        }
    }
}
