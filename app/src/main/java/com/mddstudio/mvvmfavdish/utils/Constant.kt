package com.mddstudio.mvvmfavdish.utils

object Constant {

    const val DISH_TYPE: String = "DishType"
    const val DISH_CATEGORY: String = "DishCategory"
    const val DISH_COOKING_TIME: String = "DishCookingTime"
    const val DISH_IMAGE_LOCAL: String = "local"
    const val DISH_IMAGE_ONLINE: String = "online"
    const val ALL_ITEM:String="All"
    const val FILTER_SEL:String="FilterSelection"
    const val API_KEY:String="f611ba97efb248c2a36f7276e62994d2"
    const val API_ENDL:String="recipes/random"
    const val BASE_URL:String="https://api.spoonacular.com/"


    const val LIMIT_LICENSE: String = "limitLicense"

    const val NUMBER: String = "number"

const val DISH_DETAILS:String="Dish_Details"
    const val TAGS:String="vegetarian,dessert"
    const val LIMITVALUE:Boolean=true
    const val NUMBERVSLUE:Int=1

    /**
     * This function will return the Dish Type List items.
     */
    fun dishTypes(): ArrayList<String> {
        val list = ArrayList<String>()
        list.add("breakfast")
        list.add("lunch")
        list.add("snacks")
        list.add("dinner")
        list.add("salad")
        list.add("side dish")
        list.add("dessert")
        list.add("other")
        return list
    }

    /**
     *  This function will return the Dish Category list items.
     */
    fun dishCategories(): ArrayList<String> {
        val list = ArrayList<String>()
        list.add("Pizza")
        list.add("BBQ")
        list.add("Bakery")
        list.add("Burger")
        list.add("Cafe")
        list.add("Chicken")
        list.add("Dessert")
        list.add("Drinks")
        list.add("Hot Dogs")
        list.add("Juices")
        list.add("Sandwich")
        list.add("Tea & Coffee")
        list.add("Wraps")
        list.add("Other")
        return list
    }


    /**
     *  This function will return the Dish Cooking Time list items. The time added is in Minutes.
     */
    fun dishCookTime(): ArrayList<String> {
        val list = ArrayList<String>()
        list.add("10")
        list.add("15")
        list.add("20")
        list.add("30")
        list.add("45")
        list.add("50")
        list.add("60")
        list.add("90")
        list.add("120")
        list.add("150")
        list.add("180")
        return list
    }
}