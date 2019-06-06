def availableTypes() {
    return ["testjenkinsfile", "frontend", "storage", "springboot", "tomcat", "golang"]
}

def call(Map config) {
    def type = config.get('type', 'testjenkinsfile')
    checkTypeAvailability(type)

    "$type"(config)
}

def checkTypeAvailability(type) {
    if (!availableTypes().contains(type)) {
        throw new Exception("Type ${type} is not available")
    }
}
