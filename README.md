# punch-Power
## Originally, it was made as a punch speed measurement, but just swing as much as you can :)

<p align="center">
<img src ="https://user-images.githubusercontent.com/29934506/76324753-cd749500-6329-11ea-845d-05f3c0670678.png" align ="center" width=200 height =350/>
</p>

## Punch as fast as you can , as big as you plz
<br><br>

<p align="center">
<img src ="https://user-images.githubusercontent.com/29934506/76325022-2e9c6880-632a-11ea-8d07-c82d4cf739c7.png" width=200 height =350/>
</p>

## Calculating score using x, y, z coordinates
```kotlin
override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                if(event.sensor.type != Sensor.TYPE_LINEAR_ACCELERATION)
                    return@let

                // Square each coordinate value to eliminate negative values ​​and maximize the difference
                val power =
                    Math.pow(event.values[0].toDouble(), 2.0) +  Math.pow(event.values[1].toDouble(), 2.0) +
                            Math.pow(event.values[2].toDouble(), 2.0)
```
<br><br>

<p align="center">
   <img src ="https://user-images.githubusercontent.com/29934506/76325465-acf90a80-632a-11ea-984c-4fb06669261e.png" width=200 height =350/>  <img src ="https://user-images.githubusercontent.com/29934506/76326845-90f66880-632c-11ea-9665-81b31d0a9294.jpg" width=200 height =350/> 
            
<br>            
<img src ="https://user-images.githubusercontent.com/29934506/76397916-4e31a080-63bf-11ea-9b97-036e77705e46.png" width=200 height =350/>
</p>
      
                                                   *Edit NumberFormat* 

## The score range is very wide.
## Ranking button not available.
