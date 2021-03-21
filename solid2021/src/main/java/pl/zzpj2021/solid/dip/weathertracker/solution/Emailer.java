package pl.zzpj2021.solid.dip.weathertracker.solution;


interface Emailer {
    default String generateWeatherAlert(String weatherConditions) {
        return "It is " + weatherConditions;
    }
}
