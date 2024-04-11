using MauiApp3;
using System.Text.Json;
using System.Text.RegularExpressions;


namespace MauiApp3
{
    public partial class HomePage : TabbedPage
    {
        private List<Details> cartItems = new List<Details>();

        public HomePage()
        {
            InitializeComponent();

            collectionView.ItemsSource = GetDetails();
        }

        private List<Details> GetDetails()
        {
            return new List<Details>
            {
                new Details{ Id = "1", TimeOfDay = "09.00", DayOfTheWeek = "Wednesday", TeacherName = "Albert", Date = "20/12/2023", ImageUrl = "https://cdn.britannica.com/87/21087-050-ECAD622A/Albert-Einstein.jpg"},
                new Details{ Id = "2", TimeOfDay = "10.00", DayOfTheWeek = "Wednesday", TeacherName = "Issac", Date = "27/12/2023", ImageUrl = "https://tse1.mm.bing.net/th?id=OIP.w-M0ZoM0l1P7vWPc2Aa5MgHaHa&pid=Api&P=0&h=180"},
                new Details{ Id = "3", TimeOfDay = "09.00", DayOfTheWeek = "Tuesday", TeacherName = "Newton", Date = "18/12/2023", ImageUrl = "https://pluspng.com/img-png/png-user-icon-person-icon-png-people-person-user-icon-2240.png"},
                new Details{ Id = "4", TimeOfDay = "09.00", DayOfTheWeek = "Tuesday", TeacherName = "John", Date = "12/12/2023", ImageUrl = "https://pluspng.com/img-png/png-user-icon-person-icon-png-people-person-user-icon-2240.png"},
                new Details{ Id = "5", TimeOfDay = "11.00", DayOfTheWeek = "Monday", TeacherName = "Laila", Date = "01/01/2024", ImageUrl = "https://pluspng.com/img-png/png-user-icon-person-icon-png-people-person-user-icon-2240.png"},
                new Details{ Id = "6", TimeOfDay = "10.00", DayOfTheWeek = "Monday", TeacherName = "Jennie", Date = "08/01/2024", ImageUrl = "https://pluspng.com/img-png/png-user-icon-person-icon-png-people-person-user-icon-2240.png"},
                 new Details{ Id = "7", TimeOfDay = "10.00", DayOfTheWeek = "Wednesday", TeacherName = "Marie", Date = "25/12/2023", ImageUrl = "https://pluspng.com/img-png/png-user-icon-person-icon-png-people-person-user-icon-2240.png"},
            };
        }

        private void Add_To_Cart(object sender, EventArgs e)
        {
            var button = (Button)sender;
            var selectedItem = button.BindingContext as Details;

            bool itemExists = cartItems.Any(item => item.TeacherName == selectedItem.TeacherName);

            if (itemExists)
            {
                DisplayAlert("Item Exists", "This item is already in the cart.", "OK");
            }
            else
            {
                cartItems.Add(selectedItem);

                DisplayAlert("Item Added", "The item has been added to the cart.", "OK");

                var itemLayout = new Grid();

                itemLayout.ColumnDefinitions.Add(new ColumnDefinition { Width = GridLength.Auto });
                itemLayout.ColumnDefinitions.Add(new ColumnDefinition { Width = GridLength.Star });
                itemLayout.ColumnDefinitions.Add(new ColumnDefinition { Width = GridLength.Auto });

                var itemImage = new Image { Source = selectedItem.ImageUrl, WidthRequest = 50, HeightRequest = 50 };
                itemLayout.Children.Add(itemImage);
                Grid.SetColumn(itemImage, 0); 

                var textLayout = new StackLayout { VerticalOptions = LayoutOptions.Center };

                var teacherLabel = new Label { Text = selectedItem.TeacherName, FontSize = Device.GetNamedSize(NamedSize.Medium, typeof(Label)), FontAttributes = FontAttributes.Bold };
                textLayout.Children.Add(teacherLabel);

                var dateLabel = new Label { Text = selectedItem.Date, FontAttributes = FontAttributes.Italic };
                textLayout.Children.Add(dateLabel);

                itemLayout.Children.Add(textLayout);
                Grid.SetColumn(textLayout, 1); 

                var buttonsLayout = new StackLayout { Orientation = StackOrientation.Horizontal };

                var removeButton = new Button { Text = "Remove", FontSize = Device.GetNamedSize(NamedSize.Large, typeof(Button)), FontAttributes = FontAttributes.Bold };
                removeButton.Clicked += RemoveButton_Clicked;
                buttonsLayout.Children.Add(removeButton);

                var confirmButton = new Button { Text = "Confirm", FontSize = Device.GetNamedSize(NamedSize.Large, typeof(Button)), FontAttributes = FontAttributes.Bold };
                confirmButton.Clicked += ConfirmButton_Clicked;
                buttonsLayout.Children.Add(confirmButton);

                itemLayout.Children.Add(buttonsLayout);
                Grid.SetColumn(buttonsLayout, 2); 

                cartItemsLayout.Children.Add(itemLayout);
            }
        }

        private void RemoveButton_Clicked(object sender, EventArgs e)
        {
            var removeButton = (Button)sender;
            var itemLayout = (Grid)removeButton.Parent.Parent; 

            var itemToRemove = cartItems.FirstOrDefault(item =>
            {
                var label = itemLayout.Children.OfType<StackLayout>()
                    .SelectMany(stackLayout => stackLayout.Children.OfType<Label>())
                    .FirstOrDefault(label => label.Text == item.TeacherName);

                return label != null;
            });

            if (itemToRemove != null)
                cartItems.Remove(itemToRemove);

            cartItemsLayout.Children.Remove(itemLayout);
            DisplayAlert("Item Removed", "The item has been removed from the cart.", "OK");
        }

        private async void ConfirmButton_Clicked(object sender, EventArgs e)
        {
            var confirmButton = (Button)sender;
            var itemLayout = (Grid)confirmButton.Parent.Parent; 
           
            var selectedItem = cartItems.FirstOrDefault(item =>
            {
                var label = itemLayout.Children.OfType<StackLayout>()
                    .SelectMany(stackLayout => stackLayout.Children.OfType<Label>())
                    .FirstOrDefault(label => label.Text == item.TeacherName);

                return label != null;
            });

            if (selectedItem != null)
            {
                string email = await DisplayPromptAsync("Enter Email", "Please enter your email address:", "Confirm", "Cancel", "yourname@gmail.com", -1, Keyboard.Email);

                if (!string.IsNullOrWhiteSpace(email))
                {
                    if (IsValidEmail(email))
                    {
                        SaveData(selectedItem.Id, email);

                        cartItems.Remove(selectedItem);
                        cartItemsLayout.Children.Remove(itemLayout);
                        await DisplayAlert("Booking Confirmed", "Your booking has been confirmed.", "OK");
                    }
                    else
                    {
                        await DisplayAlert("Invalid Email", "Please enter a valid email address.", "OK");
                    }
                }
            }
        }

        private void SaveData(string id, string email)
        {
            var data = new { Id = id, Email = email };
            var jsonData = JsonSerializer.Serialize(data);

            string filePath = Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.LocalApplicationData), "data.json");
            File.WriteAllText(filePath, jsonData);
        }

        private static bool IsValidEmail(string email)
        {
            string pattern = @"^\w+([-+.']\w+)*@gmail\.com$";
            Regex regex = new Regex(pattern, RegexOptions.IgnoreCase);
            return regex.IsMatch(email);
        }
        private void SearchButton_Pressed(object sender, EventArgs e)
        {
            string searchText = searchBar.Text;

            List<Details> filteredItems = GetDetails().Where(details =>
                 details.DayOfTheWeek.ToLower().Contains(searchText.ToLower()) ||
                 details.TimeOfDay.ToLower().Contains(searchText.ToLower())).ToList();

            collectionView.ItemsSource = filteredItems;
        }
    }
}