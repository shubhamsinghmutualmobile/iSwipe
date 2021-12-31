package com.mutualmobile.iswipe.data.network.utils

object NetworkUtils {
    const val WEATHER_API_KEY = "6bf5e395a1bce8e879a6175a06006663"
    const val GENERIC_ERROR_MSG = "Unexpected error occurred!"
    const val YOUTUBE_DATA_API_KEY = "AIzaSyBq6FuPOlmgsXycRmibShB1BjDRjvqzSsw"

    fun getCurrentWeatherApiUrl(city: String = "delhi", apiKey: String = WEATHER_API_KEY): String {
        return "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$apiKey"
    }

    fun getTrendingYoutubeVideosUrl(countryCode: String, apiKey: String, pageToken: String): String {
        return "https://youtube.googleapis.com/youtube/v3/videos?part=statistics%2Csnippet&chart=mostPopular&regionCode=$countryCode&key=$apiKey&pageToken=$pageToken"
    }
}

suspend fun <T> safeApiCall(block: suspend () -> T): Either<T, String> {
    runCatching { block() }
        .onSuccess {
            return Either.Type(data = it)
        }
        .onFailure { error ->
            error.message?.let { nnErrorMsg ->
                return Either.Error(errorMsg = nnErrorMsg)
            }
        }
    return Either.Error(errorMsg = NetworkUtils.GENERIC_ERROR_MSG)
}

sealed class Either<A, B> {
    class Type<A, B>(val data: A) : Either<A, B>()
    class Error<A, B>(val errorMsg: B) : Either<A, B>()
}

object TestNetworkUtils {
    const val YOUTUBE_VIDEOS_RESPONSE_SUCCESS = """
        {
    "kind": "youtube#videoListResponse",
    "etag": "Wk9jiFfuS_n0gU6tPEvmyWtCrBQ",
    "items": [
        {
            "kind": "youtube#video",
            "etag": "9JCr5HWGdY9Xsvmz95AQxyS5nrc",
            "id": "_XYHjf25GPc",
            "snippet": {
                "publishedAt": "2021-12-25T11:21:12Z",
                "channelId": "UC2bNrKQbJLphxNCd6BSnTkA",
                "title": "KALAKKACHI | Part 1 of 2 | Karikku | Comedy",
                "description": "#karikku​ #comedy #acemoney\nFinal part releasing on Jan 1st @5pm\n\nDownload Ace Money :\nhttps://play.google.com/store/apps/details?id=com.acemoney.app\n\nDirected by : Arjun Ratan\nChief Associate Director/ Executive Producer : Nikhil Prasad\nAssociate Director : Jeevan Stephen\nStory & Dialogues : Karikku Team\nCinematography : Sidharth K T\nEditor :  Anand Mathews\nMusic, BGM and final mix : Charles Nazareth\nLyrics : Arjun Ratan\nSound : Jishnu Ram\nTitles & Vfx : Binoy John, Abhijith Krishnan\nAssistant Cinematographer : Vivek V Babu \nFocus Pullers : Kiran K.P, Arjun Shaji \nDI : Sidharth K T, Vivek V Babu\nAssistant Directors : Biju Narayanan\nLighting : Amal Ambily, Sirajudheen A, Abijith Krishnan, Karun Das\nHELI CAM : Chandhu Radhakrishnan, Sarath Udhay\nCamera Unit : Black House Media\nArt : Biju Narayan, Anu K Aniyan\nMakeup : Arshad Varkala\n Music Producer : Sanoop Luis \nSound Recordist: Ayaan K\nLocation Recordist : Jishnu Ram, Antony Pallipattu\nHair Stylist : Martin Trucco\nSubtitles : Shyam Narayanan\nCAST\n Krishnachandran, Sabareesh Sajjin, Anand Mathews, Rahul Rajagopal, Vincy Sony Aloshious, Jeevan Stephen, Mitun M Das, Kiran Viyyath, Binoy John, Unni Mathews, Riju Rajeev, Hari K.C, Sirajudheen A, Nandini Gopalakrishnan\nArjun Ratan, Anu K Aniyan, Vishnu V, Amal ambily, Vivek V Babu, Aroop Sivadas, Harikrishna",
                "thumbnails": {
                    "default": {
                        "url": "https://i.ytimg.com/vi/_XYHjf25GPc/default.jpg",
                        "width": 120,
                        "height": 90
                    },
                    "medium": {
                        "url": "https://i.ytimg.com/vi/_XYHjf25GPc/mqdefault.jpg",
                        "width": 320,
                        "height": 180
                    },
                    "high": {
                        "url": "https://i.ytimg.com/vi/_XYHjf25GPc/hqdefault.jpg",
                        "width": 480,
                        "height": 360
                    },
                    "standard": {
                        "url": "https://i.ytimg.com/vi/_XYHjf25GPc/sddefault.jpg",
                        "width": 640,
                        "height": 480
                    },
                    "maxres": {
                        "url": "https://i.ytimg.com/vi/_XYHjf25GPc/maxresdefault.jpg",
                        "width": 1280,
                        "height": 720
                    }
                },
                "channelTitle": "Karikku",
                "tags": [
                    "karikku",
                    "malayalam",
                    "funny",
                    "comedy",
                    "webseries",
                    "lolan",
                    "george",
                    "karikk",
                    "karikke",
                    "film",
                    "trending",
                    "sketch",
                    "vine",
                    "flowerstv"
                ],
                "categoryId": "23",
                "liveBroadcastContent": "none",
                "localized": {
                    "title": "KALAKKACHI | Part 1 of 2 | Karikku | Comedy",
                    "description": "#karikku​ #comedy #acemoney\nFinal part releasing on Jan 1st @5pm\n\nDownload Ace Money :\nhttps://play.google.com/store/apps/details?id=com.acemoney.app\n\nDirected by : Arjun Ratan\nChief Associate Director/ Executive Producer : Nikhil Prasad\nAssociate Director : Jeevan Stephen\nStory & Dialogues : Karikku Team\nCinematography : Sidharth K T\nEditor :  Anand Mathews\nMusic, BGM and final mix : Charles Nazareth\nLyrics : Arjun Ratan\nSound : Jishnu Ram\nTitles & Vfx : Binoy John, Abhijith Krishnan\nAssistant Cinematographer : Vivek V Babu \nFocus Pullers : Kiran K.P, Arjun Shaji \nDI : Sidharth K T, Vivek V Babu\nAssistant Directors : Biju Narayanan\nLighting : Amal Ambily, Sirajudheen A, Abijith Krishnan, Karun Das\nHELI CAM : Chandhu Radhakrishnan, Sarath Udhay\nCamera Unit : Black House Media\nArt : Biju Narayan, Anu K Aniyan\nMakeup : Arshad Varkala\n Music Producer : Sanoop Luis \nSound Recordist: Ayaan K\nLocation Recordist : Jishnu Ram, Antony Pallipattu\nHair Stylist : Martin Trucco\nSubtitles : Shyam Narayanan\nCAST\n Krishnachandran, Sabareesh Sajjin, Anand Mathews, Rahul Rajagopal, Vincy Sony Aloshious, Jeevan Stephen, Mitun M Das, Kiran Viyyath, Binoy John, Unni Mathews, Riju Rajeev, Hari K.C, Sirajudheen A, Nandini Gopalakrishnan\nArjun Ratan, Anu K Aniyan, Vishnu V, Amal ambily, Vivek V Babu, Aroop Sivadas, Harikrishna"
                },
                "defaultAudioLanguage": "ml"
            },
            "statistics": {
                "viewCount": "2712717",
                "likeCount": "311646",
                "favoriteCount": "0",
                "commentCount": "17788"
            }
        },
        {
            "kind": "youtube#video",
            "etag": "d_MVqeioTjSAB5CwoOo3nOjyIAc",
            "id": "mUDZdBop9gU",
            "snippet": {
                "publishedAt": "2021-12-23T16:10:07Z",
                "channelId": "UCmse5JbKneJqVyerfhDVYvQ",
                "title": "Radhe Shyam (Telugu) Theatrical Trailer | Prabhas | Pooja Hegde | Radha Krishna | UV Creations",
                "description": "Radhe Shyam Theatrical Trailer on UV Creations. RadheShyam 2022 Indian Romantic Drama film ft. Prabhas and Pooja Hegde. Written and directed by Radha Krishna Kumar. Music by Justin Prabhakaran on T-Series, Sound design by Resul Pookutty. Presented by Krishnam Raju under Gopi Krishna Movies. Produced by UV Creations and T-Series.\n\nThe epic love tale Radhe Shyam will be releasing worldwide on 14th Jan 2022 in Telugu, Tamil, Hindi, Kannada, Malayalam, Chinese, and Japanese.\n\nThe prestigious Indian multi-lingual film Radhe Shyam also stars Sachin Khedekar, Priyadarsi Pulikonda, Bhagyashree, Jagapathi Babu, Murli Sharma, Kunaal Roy Kapoor, Riddhi Kumar, Sasha Chettri, Sathyan, and others. \n\n#Prabhas #RadheShyam #RadheShyamTrailer #PoojaHegde #RadhaKrishnaKumar #UVCreations #TSeries #Prabhas20 #RadheShyamOfficialTrailer #RadheShyamTheatricalTrailer\n\nMovie Details: \n\nMovie: Radhe Shyam\n\nLanguages - Telugu, Hindi, Tamil, Kannada, Malayalam, Chinese, Japanese\n\nCast: Prabhas, Pooja Hegde, Sachin Khedekar, Priyadarsi Pulikonda, Bhagyashree, Jagapathi Babu, Murli Sharma, Kunaal Roy Kapoor, Riddhi Kumar, Sasha Chettri, Sathyan and others. \n\nProduced by UV Creations and T-Series \nPresented by Krishnam Raju under Gopi Krishna Movies\nWritten and Directed by Radha Krishna Kumar\nAudio - T Series \nProducers - Vamsi, Pramod, Praseedha\nDOP - Manoj Paramahamsa\nMusic - Justin Prabhakaran \nProduction Designer - Raveendar \nEditor -  Kotagiri Venkateswara Rao \nVisual Effects - Kamalakannan \nVisual Development - Ajay Singh Supahiya\nAction - Nick Powell\nSound Design - Resul Pookutty (cas, mpse), Amrit Pritam Dutta (mpse) \nChoreographer - Vaibhavi Merchant \nDialogues - Radha Krishna Kumar, Jay Krishna \nLyrics - Krishna Kanth \nCostumes - Thota Vijay Bhaskar, Eka Lakhani\nCreative Head - Anil Kumar Upadyaula\nAvid Editor - Satya G \nDI - B2H \nFirst AD - Anu K Reddy, Arun Thevar \nPublicity Designs - Kabilan Chelliah\nDigital Agency - Whackedout Media, Little Monks \nPR - Spice \nMarketing Consultant - Varun Gupta (max)\n\nClick here to watch:\n\nRadhe Shyam Teaser : https://youtu.be/s3s0XVBq1zE\n\nRadhe Shyam Telugu Glimpse: https://youtu.be/3DNiJrqW_U8\n\nRadhe Shyam Pre Teaser: https://youtu.be/GGmK2iW9ZgM\n\nBeats Of Radhe Shyam: https://youtu.be/Ffp2i537Fiw\n\nSaaho Movie Making : https://youtu.be/kEeiuf5u__s\n\nPrabhas Mirchi Video Songs: https://bit.ly/2q7VhzW  \n\nPrabhas Saaho Official Teasers: http://bit.ly/2y866nw\n\nBhale Bhale Magadivoy Video Songs: http://bit.ly/2D9e5V0\n\nRun Raja Run Video Songs: http://bit.ly/2AHHrqQ\n\nFor More Updates About Radhe Shyam & UV Creations:\n\nLike: https://www.facebook.com/UVCTheMovieMakers\nFollow: https://www.instagram.com/uvcreationsofficial  \nFollow: https://twitter.com/UV_Creations\nSubscribe: https://www.youtube.com/c/UVCreations",
                "thumbnails": {
                    "default": {
                        "url": "https://i.ytimg.com/vi/mUDZdBop9gU/default.jpg",
                        "width": 120,
                        "height": 90
                    },
                    "medium": {
                        "url": "https://i.ytimg.com/vi/mUDZdBop9gU/mqdefault.jpg",
                        "width": 320,
                        "height": 180
                    },
                    "high": {
                        "url": "https://i.ytimg.com/vi/mUDZdBop9gU/hqdefault.jpg",
                        "width": 480,
                        "height": 360
                    },
                    "standard": {
                        "url": "https://i.ytimg.com/vi/mUDZdBop9gU/sddefault.jpg",
                        "width": 640,
                        "height": 480
                    },
                    "maxres": {
                        "url": "https://i.ytimg.com/vi/mUDZdBop9gU/maxresdefault.jpg",
                        "width": 1280,
                        "height": 720
                    }
                },
                "channelTitle": "UV Creations",
                "tags": [
                    "Radhe Shyam Telugu Trailer",
                    "Radhe Shyam",
                    "Prabhas",
                    "Pooja Hegde",
                    "Radha Krishna Kumar",
                    "Justin Prabhakaran",
                    "UV Creations",
                    "Radhe Shyam Official Trailer",
                    "Prabhas Radhe Shyam Trailer",
                    "Radhe Shyam Telugu Movie",
                    "Prabhas New Movie Trailer",
                    "Radhe Shyam Songs",
                    "Radhe Shyam Trailer",
                    "Prabhas Radhe Shyam",
                    "Radhe Shyam Telugu Movie Trailer",
                    "Prabhas Latest Movie Trailer",
                    "Prabhas Movies",
                    "Radhe Shyam From 14th jan 2022"
                ],
                "categoryId": "24",
                "liveBroadcastContent": "none",
                "localized": {
                    "title": "Radhe Shyam (Telugu) Theatrical Trailer | Prabhas | Pooja Hegde | Radha Krishna | UV Creations",
                    "description": "Radhe Shyam Theatrical Trailer on UV Creations. RadheShyam 2022 Indian Romantic Drama film ft. Prabhas and Pooja Hegde. Written and directed by Radha Krishna Kumar. Music by Justin Prabhakaran on T-Series, Sound design by Resul Pookutty. Presented by Krishnam Raju under Gopi Krishna Movies. Produced by UV Creations and T-Series.\n\nThe epic love tale Radhe Shyam will be releasing worldwide on 14th Jan 2022 in Telugu, Tamil, Hindi, Kannada, Malayalam, Chinese, and Japanese.\n\nThe prestigious Indian multi-lingual film Radhe Shyam also stars Sachin Khedekar, Priyadarsi Pulikonda, Bhagyashree, Jagapathi Babu, Murli Sharma, Kunaal Roy Kapoor, Riddhi Kumar, Sasha Chettri, Sathyan, and others. \n\n#Prabhas #RadheShyam #RadheShyamTrailer #PoojaHegde #RadhaKrishnaKumar #UVCreations #TSeries #Prabhas20 #RadheShyamOfficialTrailer #RadheShyamTheatricalTrailer\n\nMovie Details: \n\nMovie: Radhe Shyam\n\nLanguages - Telugu, Hindi, Tamil, Kannada, Malayalam, Chinese, Japanese\n\nCast: Prabhas, Pooja Hegde, Sachin Khedekar, Priyadarsi Pulikonda, Bhagyashree, Jagapathi Babu, Murli Sharma, Kunaal Roy Kapoor, Riddhi Kumar, Sasha Chettri, Sathyan and others. \n\nProduced by UV Creations and T-Series \nPresented by Krishnam Raju under Gopi Krishna Movies\nWritten and Directed by Radha Krishna Kumar\nAudio - T Series \nProducers - Vamsi, Pramod, Praseedha\nDOP - Manoj Paramahamsa\nMusic - Justin Prabhakaran \nProduction Designer - Raveendar \nEditor -  Kotagiri Venkateswara Rao \nVisual Effects - Kamalakannan \nVisual Development - Ajay Singh Supahiya\nAction - Nick Powell\nSound Design - Resul Pookutty (cas, mpse), Amrit Pritam Dutta (mpse) \nChoreographer - Vaibhavi Merchant \nDialogues - Radha Krishna Kumar, Jay Krishna \nLyrics - Krishna Kanth \nCostumes - Thota Vijay Bhaskar, Eka Lakhani\nCreative Head - Anil Kumar Upadyaula\nAvid Editor - Satya G \nDI - B2H \nFirst AD - Anu K Reddy, Arun Thevar \nPublicity Designs - Kabilan Chelliah\nDigital Agency - Whackedout Media, Little Monks \nPR - Spice \nMarketing Consultant - Varun Gupta (max)\n\nClick here to watch:\n\nRadhe Shyam Teaser : https://youtu.be/s3s0XVBq1zE\n\nRadhe Shyam Telugu Glimpse: https://youtu.be/3DNiJrqW_U8\n\nRadhe Shyam Pre Teaser: https://youtu.be/GGmK2iW9ZgM\n\nBeats Of Radhe Shyam: https://youtu.be/Ffp2i537Fiw\n\nSaaho Movie Making : https://youtu.be/kEeiuf5u__s\n\nPrabhas Mirchi Video Songs: https://bit.ly/2q7VhzW  \n\nPrabhas Saaho Official Teasers: http://bit.ly/2y866nw\n\nBhale Bhale Magadivoy Video Songs: http://bit.ly/2D9e5V0\n\nRun Raja Run Video Songs: http://bit.ly/2AHHrqQ\n\nFor More Updates About Radhe Shyam & UV Creations:\n\nLike: https://www.facebook.com/UVCTheMovieMakers\nFollow: https://www.instagram.com/uvcreationsofficial  \nFollow: https://twitter.com/UV_Creations\nSubscribe: https://www.youtube.com/c/UVCreations"
                },
                "defaultAudioLanguage": "en"
            },
            "statistics": {
                "viewCount": "31186256",
                "likeCount": "774548",
                "favoriteCount": "0",
                "commentCount": "26347"
            }
        },
        {
            "kind": "youtube#video",
            "etag": "-lCoLoapBCE-iUE7NV8F-7aaRiU",
            "id": "nahOCys752Q",
            "snippet": {
                "publishedAt": "2021-12-25T06:00:10Z",
                "channelId": "UCpZe6c7I8kkQuME1T4uMa_g",
                "title": "🚨stay alert 🇮🇳🇧🇩🇵🇰 || everyone should be aware of these frauds #shorts",
                "description": "#shorts \ni've donated all my 2021 earning to full-fill the dreams of many families \nbut how?\nwatch a new short everyday around 10am",
                "thumbnails": {
                    "default": {
                        "url": "https://i.ytimg.com/vi/nahOCys752Q/default.jpg",
                        "width": 120,
                        "height": 90
                    },
                    "medium": {
                        "url": "https://i.ytimg.com/vi/nahOCys752Q/mqdefault.jpg",
                        "width": 320,
                        "height": 180
                    },
                    "high": {
                        "url": "https://i.ytimg.com/vi/nahOCys752Q/hqdefault.jpg",
                        "width": 480,
                        "height": 360
                    }
                },
                "channelTitle": "Harsha Sai - For You Hindi",
                "categoryId": "24",
                "liveBroadcastContent": "none",
                "localized": {
                    "title": "🚨stay alert 🇮🇳🇧🇩🇵🇰 || everyone should be aware of these frauds #shorts",
                    "description": "#shorts \ni've donated all my 2021 earning to full-fill the dreams of many families \nbut how?\nwatch a new short everyday around 10am"
                },
                "defaultAudioLanguage": "hi"
            },
            "statistics": {
                "viewCount": "1646298",
                "likeCount": "166575",
                "favoriteCount": "0",
                "commentCount": "1090"
            }
        },
        {
            "kind": "youtube#video",
            "etag": "UmUdEwO5abFwCSzZNLKEH4X5wvs",
            "id": "xz9yB7nYRbA",
            "snippet": {
                "publishedAt": "2021-12-22T07:03:22Z",
                "channelId": "UC56dNUTok1pgvpY75CsJQ4Q",
                "title": "Salute to Indian Army❤️ || Gulshan kalra #shorts",
                "description": "",
                "thumbnails": {
                    "default": {
                        "url": "https://i.ytimg.com/vi/xz9yB7nYRbA/default.jpg",
                        "width": 120,
                        "height": 90
                    },
                    "medium": {
                        "url": "https://i.ytimg.com/vi/xz9yB7nYRbA/mqdefault.jpg",
                        "width": 320,
                        "height": 180
                    },
                    "high": {
                        "url": "https://i.ytimg.com/vi/xz9yB7nYRbA/hqdefault.jpg",
                        "width": 480,
                        "height": 360
                    }
                },
                "channelTitle": "Gulshan Kalra",
                "categoryId": "26",
                "liveBroadcastContent": "none",
                "localized": {
                    "title": "Salute to Indian Army❤️ || Gulshan kalra #shorts",
                    "description": ""
                }
            },
            "statistics": {
                "viewCount": "18600553",
                "likeCount": "1939380",
                "favoriteCount": "0",
                "commentCount": "5058"
            }
        },
        {
            "kind": "youtube#video",
            "etag": "MLkZLlgLq1hDp2kLW6qBqIW5msA",
            "id": "u67Jz-I7cD4",
            "snippet": {
                "publishedAt": "2021-12-25T08:34:55Z",
                "channelId": "UCRSVI-eLa2y4BVCJNf1-aSA",
                "title": "Gua Ghia //Odia Viral Song //Funny Angulia//Khordha Toka//Mr Dhenkanalia//Asad Nizam",
                "description": "♪ FUNNY ANUGULIA PRESENTS IN ASSOCIATION WITH WEDNINE ENTERTAINMENT and THE ENTERTAINER\n♪ GUA GHIA ♪\n\n►Starring : BUNTY R SAMAL & LILLY\n►Direction:- DIBYA\n►Ass. Director:-Kirti\n►DOP : Akshay ku. Nayak\n►Ass. DOP:-  Muna\n►Ass.Director:- Jagadish Paikaray & Gudu\n►Coreographer-Badmash Bunty\n►Producer:-Bunty r Samal\n►Co-Producer:- Prithviraj Pattnaik\n►Edit:- Manas Kumar Sahoo\n►Di:- Gugu\n►Production Manager:- Srikant Singh\n►Music- Asad Nizam\n►Lyrics- Ranjan Nayak\n►Singer- Bunty R Samal\n►Jimmy:-Abhinas\n►Lights:-Tsce(Cuttack)\n►Poster- Nandan Goswami\n►Digital partner-One digital\nSpecial Thanks to Prithvi Raj Pattnaik and team❤️\n►Sarbe Sarba- Badu d\n\nThis video only for entertainment purpose we dont have any intension to critisize anyone or other's views.\r\nWe are strictly aware about youtube copyright rules\r\n\r\nPlease like and Share the video. \r\nFor more updates subscribe to Funny Angulia.\r\n\r\n\r\n\r\nCopyrighted content used under the fair use exception for review and commentary.\r\n*copyright Disclaimer under section 107 of the copyright act 1976,allowance is made for fare use for purposes such as criticism,comment,news reporting,teaching,scholarship and reserch.",
                "thumbnails": {
                    "default": {
                        "url": "https://i.ytimg.com/vi/u67Jz-I7cD4/default.jpg",
                        "width": 120,
                        "height": 90
                    },
                    "medium": {
                        "url": "https://i.ytimg.com/vi/u67Jz-I7cD4/mqdefault.jpg",
                        "width": 320,
                        "height": 180
                    },
                    "high": {
                        "url": "https://i.ytimg.com/vi/u67Jz-I7cD4/hqdefault.jpg",
                        "width": 480,
                        "height": 360
                    },
                    "standard": {
                        "url": "https://i.ytimg.com/vi/u67Jz-I7cD4/sddefault.jpg",
                        "width": 640,
                        "height": 480
                    },
                    "maxres": {
                        "url": "https://i.ytimg.com/vi/u67Jz-I7cD4/maxresdefault.jpg",
                        "width": 1280,
                        "height": 720
                    }
                },
                "channelTitle": "Funny Angulia",
                "tags": [
                    "funny angulia",
                    "odia comedy",
                    "Gua",
                    "Gua ghia",
                    "Gua ghia song",
                    "Gua ghia viral song",
                    "odia viral song",
                    "comedy song",
                    "khordha toka",
                    "Mr dhenkanalia comedy",
                    "odia new music video",
                    "Mr gulua comedy",
                    "chandan biswal comedy",
                    "raju das comedy",
                    "dhenkanalia comedy",
                    "angulia bunty song",
                    "Gua ghia arua bhata"
                ],
                "categoryId": "24",
                "liveBroadcastContent": "none",
                "localized": {
                    "title": "Gua Ghia //Odia Viral Song //Funny Angulia//Khordha Toka//Mr Dhenkanalia//Asad Nizam",
                    "description": "♪ FUNNY ANUGULIA PRESENTS IN ASSOCIATION WITH WEDNINE ENTERTAINMENT and THE ENTERTAINER\n♪ GUA GHIA ♪\n\n►Starring : BUNTY R SAMAL & LILLY\n►Direction:- DIBYA\n►Ass. Director:-Kirti\n►DOP : Akshay ku. Nayak\n►Ass. DOP:-  Muna\n►Ass.Director:- Jagadish Paikaray & Gudu\n►Coreographer-Badmash Bunty\n►Producer:-Bunty r Samal\n►Co-Producer:- Prithviraj Pattnaik\n►Edit:- Manas Kumar Sahoo\n►Di:- Gugu\n►Production Manager:- Srikant Singh\n►Music- Asad Nizam\n►Lyrics- Ranjan Nayak\n►Singer- Bunty R Samal\n►Jimmy:-Abhinas\n►Lights:-Tsce(Cuttack)\n►Poster- Nandan Goswami\n►Digital partner-One digital\nSpecial Thanks to Prithvi Raj Pattnaik and team❤️\n►Sarbe Sarba- Badu d\n\nThis video only for entertainment purpose we dont have any intension to critisize anyone or other's views.\r\nWe are strictly aware about youtube copyright rules\r\n\r\nPlease like and Share the video. \r\nFor more updates subscribe to Funny Angulia.\r\n\r\n\r\n\r\nCopyrighted content used under the fair use exception for review and commentary.\r\n*copyright Disclaimer under section 107 of the copyright act 1976,allowance is made for fare use for purposes such as criticism,comment,news reporting,teaching,scholarship and reserch."
                },
                "defaultAudioLanguage": "or"
            },
            "statistics": {
                "viewCount": "884586",
                "likeCount": "118012",
                "favoriteCount": "0",
                "commentCount": "5986"
            }
        }
    ],
    "nextPageToken": "CAUQAA",
    "pageInfo": {
        "totalResults": 200,
        "resultsPerPage": 5
    }
}
    """
    const val WEATHER_RESPONSE_SUCCESS = """{
  "coord": {
    "lon": -122.08,
    "lat": 37.39
  },
  "weather": [
    {
      "id": 800,
      "main": "Clear",
      "description": "clear sky",
      "icon": "01d"
    }
  ],
  "base": "stations",
  "main": {
    "temp": 282.55,
    "feels_like": 281.86,
    "temp_min": 280.37,
    "temp_max": 284.26,
    "pressure": 1023,
    "humidity": 100
  },
  "visibility": 16093,
  "wind": {
    "speed": 1.5,
    "deg": 350
  },
  "clouds": {
    "all": 1
  },
  "dt": 1560350645,
  "sys": {
    "type": 1,
    "id": 5122,
    "message": 0.0139,
    "country": "US",
    "sunrise": 1560343627,
    "sunset": 1560396563
  },
  "timezone": -25200,
  "id": 420006353,
  "name": "Mountain View",
  "cod": 200
  }                         
    """
}
