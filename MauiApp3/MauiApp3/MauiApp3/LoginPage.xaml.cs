namespace MauiApp3;

public partial class LoginPage : ContentPage
{
    public LoginPage()
    {
        InitializeComponent();
    }

    private async void LoginButton_Clicked(object sender, EventArgs e)
    {
        string enteredUsername = usernameEntry.Text;

        if (enteredUsername == "sm9669f")
        {
            await Navigation.PushAsync(new HomePage());
        }
        else
        {
            await DisplayAlert("Invalid Username", "You are not authorized to access this page.", "OK");
        }
    }
}