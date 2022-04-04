# AndroidFruitSelector

# Práctica 1 - Sistemas Móviles y Ubicuos

This application has been designed with the purpose of creating an online Fruit Shop, it contains a fruit selector,
an item SeekBar and a basket item list where the basket items are displayed.

Next I will be naming each UI component and describing how it has been implemented.

## UI Elements
### CustomSpinnerSelector

This elements its used for the user to select the desired fruit to buy. It contains an image of the fruit
and its name, also a custom background. The popup menu contains every available fruit, each item will contain
the image and its name, and it also has a custom background.

For this Custom Spinner I have developed a CustomSpinner class which sets the spinner background when the popup is 
opened or closed by setting the dropdown arrow up and down.

As I mentioned before, this spinner has custom views for each item, these views are built in CustomSpinnedAdapter class
which set as the CustomSpinner adapter.

Finally, the CustomSpinnerSelectorListener class will be the one that controls sets the current fruit item and resets the 
SeekBar and the fruitQuantity when the item is changed. It also sets the item visibility when no fruit has been selected yet.

### SeekBar

This progress bar its used for allowing the user to select the number of fruit items he wants to buy. Its maximum value its 100.
Its CustomSeekBarListener will react when the progress changes and will update the selected fruit quantity and also will update the 
quantity UI text and price.

### Basket

It consists of a RecyclerView with a custom adapter that will be the one that build its views. This adapter contains a ViewHolder which 
will fill the inflated custom view. This custom view contains the fruit image, its name and the quantity the user bought.

### _Add to basket_ button

This button will update the basket price, update de basket RecyclerView and reset every value.

## Others
### FruitItems

This is an enum which contains every fruit element.

### BasketItem

This is a data class which holds a basket item data.

### Layouts & Drawables

* Layouts: designed for vertical and landscape views, fruit item, basket fruit item and the _Select a fruit_ selector option. Located at _res/layout_
  
* Drawables: designed every custom background, here are the fruit item pics. Located at _res/drawables_

## ScreenShots
### Vertical

<img src="https://github.com/dhrodao/AndroidFruitSelector/blob/master/docs/vertical.png" alt="Demo vert" data-canonical-src="docs/screenshot.png" width="300"/>
<img src="https://github.com/dhrodao/AndroidFruitSelector/blob/master/docs/vertical_popup.png" alt="Demo vert popup" data-canonical-src="docs/screenshot.png" width="300"/>

### Landscape

<img src="https://github.com/dhrodao/AndroidFruitSelector/blob/master/docs/landscape.png" alt="Demo lands" data-canonical-src="docs/screenshot.png" width="300"/>
<img src="https://github.com/dhrodao/AndroidFruitSelector/blob/master/docs/landscape_popup.png" alt="Demo lands popup" data-canonical-src="docs/screenshot.png" width="300"/>

# Práctica 2 - Sistemas Móviles y Ubicuos

The design of this application is based on the previous one, but this time I have implemented some new shops that are based on the fruit shop model. Also, I have added a demo chat,
inbox and sent messages screens.

## Data persistence

For the UI items persistent along the execution of the application, I have implemented a MainViewModel which holds an instance of a ShopViewModel class for each shop that I've added.
It also holds the current global basket items and the current global basket price.

## Login

This is a demo login which has a username field and stores the username from the input in the MainViewModel "username" variable. From this fragment the user is redirected to the landing page.

## Landing page

This is a simple plage which show the store image, a welcome text for the user and a simple description of the app.

## Product details fragment

For this section I have implemented a ProductDetailsFragment which will be the one that will be displayed when the user clicks on a product item. This fragment will show
a picture of the product, its name and its price. It receives a bundle which contains the ItemType thas has been selected, and with this information it will get the correct 
ViewModel from the MainViewModel in order to get the current selected item, stored in the \_selectedItem variable in the ViewModel.

## Chat, Inbox and Sent Messages

All this screens have been designed in the same way, I've designed a chat layout, a sent message layout and a received message layout. From now the messages are stored in an
ArrayList of Message objects. Then, they are displayed on a RecyclerView applying the sent message layout of the received message layout depending if the username within the Message
object is the same as the username stored in the MainViewModel or not.

## Demo

![Práctica 2 demo](https://youtu.be/Ir9hR1AvDXs)