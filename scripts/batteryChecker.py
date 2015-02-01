# In cron: */1 * * * * echo "job every minute"

from subprocess import call, check_output

percentage = int(check_output("cat /sys/class/power_supply/BAT0/capacity", shell=True))

if percentage < 20:

    message = "Battery Warning You're almost dead! " + str(percentage) + "% left!"
    command = "notify-send " + str(percentage)

    # call("notify-send", "Battery Warning", "You're almost dead! " + str(percentage) + "% left!")
    call(command.split(), shell=False)
    print("Remaining battery: ", percentage, "%")
